package com.akassis.gamedealscraper.scraper;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class Convert {

    public static URL stringToURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BigDecimal stringToBigDecimal(String number) {
        return BigDecimal.valueOf(Double.valueOf(number));
    }

    public static LocalDate dateToLocalDate (Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDate epochSecondsToLocalDate(Long seconds) {
        return Instant.ofEpochSecond(seconds).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static List<String> jsonElementToStringList(JsonNode node, String element) {
        String rawElement = node.get(element).toString();
        String[] elementArray = rawElement.replaceAll("\\[|\\]|\"", "").split(",");
        return Arrays.asList(elementArray);
    }

    public static String capitalizeFirstLetter(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

}
