package com.adem.currency_bot.service;

import com.adem.currency_bot.model.UserMessage;
import com.adem.currency_bot.repository.UserMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMessageService {
    private final UserMessageRepository repository;

    public void save(UserMessage userMessage){
        repository.save(userMessage);
    }
    public List<UserMessage> getAllByChatId(Long chatId){
        return repository.findAllByChatId(chatId).orElse(new ArrayList<>());
    }
}