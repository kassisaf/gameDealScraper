package com.akassis.gamedealscraper.utils;

import java.util.ResourceBundle;

public abstract class Logger {
    private static final ResourceBundle props = ResourceBundle.getBundle("application");

    public static String getTimeStamp(){
        return new java.sql.Timestamp(System.currentTimeMillis()).toString() + "  ";
    }

    public static void println(String string) {
        System.out.println(getTimeStamp() + string);
    }
    public static void println(String prefix, String string) {
        System.out.println(getTimeStamp() + "[" + prefix.toUpperCase() + "] " + string);
    }

    public static String getVersion() {
        return props.getString("info.build.version");
    }
    public static String getGroupId() {
        return props.getString("info.build.groupId");
    }
    public static String getArtifactId() {
        return props.getString("info.build.artifactId");
    }
    public static String getAppId() {
        return getGroupId() + "." + getArtifactId();
    }

}