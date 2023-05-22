package com.adem.currency_bot.listener;

import com.adem.currency_bot.api.CurrencyConverter;
import com.adem.currency_bot.messages.BotMessages;
import com.adem.currency_bot.model.UserMessage;
import com.adem.currency_bot.parser.DateTimeParser;
import com.adem.currency_bot.parser.StringDoubleParser;
import com.adem.currency_bot.service.UserMessageService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

//Class with business logic

@Slf4j
@Component
@RequiredArgsConstructor
public class BotUpdatesListener implements UpdatesListener {
    private final TelegramBot telegramBot;
    private final UserMessageService service;
    private final CurrencyConverter currencyConverter;


    @PostConstruct
    public void init(){
        telegramBot.setUpdatesListener(this);
    }

    private void sendMessage(Long chatId, String message){
        SendMessage sendMessage =new SendMessage(chatId,message);
        SendResponse response = telegramBot.execute(sendMessage);
        if (!response.isOk()){
            log.error("Error while sending message",response.description());
        }
    }


    @Override
    public int process(List<Update> updateList) {

        updateList.stream()
                .filter(update -> update.message()!=null)
                .forEach(update -> {
                    log.info("Get updates: "+update);
                    Message message = update.message();
                    Long chatId = message.chat().id();
                    String text = message.text();
                    String username = message.chat().username();
                    Integer messageId = message.messageId();
                    service.save(new UserMessage(username,text,chatId,messageId));
                    if (text.equals("/start")){
                        sendMessage(chatId, BotMessages.WELCOME_MESSAGE);
                    }
                    else if (text.equals("/help")){
                        sendMessage(chatId, BotMessages.HELP_MESSAGE);
                    }
                    else if (text.equals("/rates")){
                        try{
                            String rates = DateTimeParser.getCurrentDateTime()+"USD к KZT - "+ currencyConverter.getUsdToKztRate()+"\n" +
                                    "KZT к USD - "+ currencyConverter.getKztToUsdRate();
                            sendMessage(chatId,rates);
                        }
                        catch (IOException e){
                            sendMessage(chatId,"Ошибка при получении курса USD к KZT на стороне сервера.");
                            log.error("Error while getting API response: "+e.getMessage());
                        }
                    }
                    else if (text.matches(BotMessages.USD_REGEX)){
                        try{
                            double amount = StringDoubleParser.parseStringToDouble(text);
                            sendMessage(chatId,"Сконвертированное значение:\n"+currencyConverter.calculateUsdToKzt(amount)+" KZT");}
                        catch (NumberFormatException numberFormatException){
                            sendMessage(chatId,BotMessages.NUMBER_FORMAT_EXCEPTION);
                        }
                        catch (IOException e){
                            sendMessage(chatId,BotMessages.IOEXCEPTION_MESSAGE);
                            log.error("Error while getting API response: "+e.getMessage());
                        }
                    }
                    else if (text.matches(BotMessages.KZT_REGEX)){
                        try{
                            double amount = StringDoubleParser.parseStringToDouble(text);
                            sendMessage(chatId,"Сконвертированное значение:\n"+currencyConverter.calculateKztToUsd(amount)+" USD");
                        }
                        catch (NumberFormatException numberFormatException){
                            sendMessage(chatId,BotMessages.NUMBER_FORMAT_EXCEPTION);
                        }
                        catch (IOException e){
                            sendMessage(chatId,BotMessages.IOEXCEPTION_MESSAGE);
                            log.error("Error while getting API response: "+e.getMessage());
                        }
                    }
                    else if (text.equals("/history")){
                        List<UserMessage> userMessages = service.getAllByChatId(chatId);
                        if (!userMessages.isEmpty()){
                            StringBuilder builder = new StringBuilder();
                            for (UserMessage user_Message:userMessages){
                                builder.append(user_Message.getText()).append("\n");
                            }
                            sendMessage(chatId,"Ваша история:\n"+builder);
                        }
                        else {
                            sendMessage(chatId,"Ваша история пуста!");
                        }
                    }
                    else {
                        sendMessage(chatId,BotMessages.UNKNOWN_COMMAND);
                    }

                });


        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}

