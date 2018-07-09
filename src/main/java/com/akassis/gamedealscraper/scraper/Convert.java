package com.akassis.gamedealscraper.scraper;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class Convert {

    public static URL stringToURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            System.out.println(Scraper.getTimeStamp() + "Could not convert to URL: " + url);
            e.printStackTrace();
        }
        return null;
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

    public static List<String> stringToList(String string) {
        List<String> list = new ArrayList<>();
        list.add(string);
        return list;
    }

}
