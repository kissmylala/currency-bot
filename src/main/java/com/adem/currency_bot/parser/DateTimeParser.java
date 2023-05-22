package com.adem.currency_bot.parser;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeParser {
    public static String getCurrentDateTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("dd.MM.yyyy HH:mm:ss"));
        String currentDate = currentDateTime.format(formatter);
        return "Курс на "+currentDate+" составляет:\n";
    }
}
