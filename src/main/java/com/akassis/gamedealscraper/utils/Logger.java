package com.akassis.gamedealscraper.utils;

public abstract class Logger {
    public static String getTimeStamp(){
        return new java.sql.Timestamp(System.currentTimeMillis()).toString() + "  ";
    }

    public static void println(String string) {
        System.out.println(getTimeStamp() + string);
    }
    public static void println(String prefix, String string) {
        System.out.println(getTimeStamp() + "[" + prefix.toUpperCase() + "] " + string);
    }
}