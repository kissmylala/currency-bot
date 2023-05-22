package com.adem.currency_bot.parser;

public class StringDoubleParser {
    public static double parseStringToDouble(String stringToParse){
        String amountString = stringToParse.replaceAll("[^\\d.]","");
        double amount = Double.parseDouble(amountString);
        return amount;
    }
}
