package com.akassis.gamedealscraper.utils;

public abstract class Logger {
    public static String getTimeStamp(){
        return new java.sql.Timestamp(System.currentTimeMillis()).toString();
    }
}
