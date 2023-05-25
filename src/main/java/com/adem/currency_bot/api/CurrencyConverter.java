package com.adem.currency_bot.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Component
@RequiredArgsConstructor
public class CurrencyConverter {

    @Value("${api.url.kzt}")
    private final String USD_TO_KZT_RATE="https://v6.exchangerate-api.com/v6/YOUR_EXCHANGER_TOKEN/pair/USD/KZT";
    @Value("${api.url.usd}")
    private final String KZT_TO_USD_RATE="https://v6.exchangerate-api.com/v6/YOUR_EXCHANGER_TOKEN/pair/KZT/USD";


    //method to get rates by URL
    private double executeRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.message());
            }
            String responseBody = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            if (jsonNode.has("conversion_rate")) {
                return jsonNode.get("conversion_rate").asDouble();
            } else {
                throw new IOException("Invalid API response: " + responseBody);
            }
        }
    }




    public double getUsdToKztRate() throws IOException {
        return   executeRequest(USD_TO_KZT_RATE);
    }

    public double getKztToUsdRate() throws IOException {
        return executeRequest(KZT_TO_USD_RATE);
    }

    //methods to calculate rates between USD AND KZT
    public String calculateUsdToKzt(double usd) throws IOException {
        double usdToKztRate = getUsdToKztRate();
        BigDecimal result = new BigDecimal((usd * usdToKztRate)).setScale(3, RoundingMode.HALF_UP);
        DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
        decimalFormat.setGroupingUsed(true);
        String formattedResult = decimalFormat.format(result);

        return formattedResult;
    }

    public String calculateKztToUsd(double kzt) throws IOException {
        double kztToUsdRate = getUsdToKztRate();
        BigDecimal result = new BigDecimal((kzt * (1 / kztToUsdRate))).setScale(3, RoundingMode.HALF_UP);
        DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
        decimalFormat.setGroupingUsed(true);
        String formattedResult = decimalFormat.format(result);

        return formattedResult;
    }




}

