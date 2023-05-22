package com.adem.currency_bot.messages;

//constant messages
public class BotMessages {

    public static final String USD_REGEX = "^\\d+(\\.\\d+)?\\s?\\$";

    public static final String KZT_REGEX="\\d+(\\.\\d+)?\\s?([Тт]|[Tt]|тенге|KZT)";

    public static final String WELCOME_MESSAGE = "Привет! Я помогу вам сконвертировать тенге в доллары и наоборот.\n" +
            "Просто отправьте мне сообщение в формате '100Т' или '100$' (без кавычек).\n" +
            "Для получения дополнительной информации о доступных командах введите /help.";

    public static final String HELP_MESSAGE = "Бот понимает следующие команды:\n" +
            "100T\n" +
            "100$\n" +
            "100KZT\n"+
            "100 тенге\n"+
            "/history - история всех ваших конвертаций\n" +
            "/rates - курс доллар/тенге, тенге/доллар";
    public static final String IOEXCEPTION_MESSAGE = "Ошибка при получении курса USD к KZT на стороне сервера.\n" +
            "Повторите свой запрос через некоторое время.";
    public static final String NUMBER_FORMAT_EXCEPTION="Вы ввели некорректный формат, бот понимает следующие форматы:\n" +
            "100$ - для конвертации USD в KZT\n" +
            "100Т,100тенге,100KZT - для конвертации KZT в USD\n"
            ;
    public static final String UNKNOWN_COMMAND = "Неизвестная команда, для получения списка доступных команд" +
            " введите /help.";


}
