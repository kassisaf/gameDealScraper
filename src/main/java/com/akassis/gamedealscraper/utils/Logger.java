package com.akassis.gamedealscraper.utils;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import java.io.FileReader;
import java.util.Objects;

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

    private static Model getProjectInfo() {
        try {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            return reader.read(new FileReader("pom.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getProjectVersion() {
        return Objects.requireNonNull(getProjectInfo()).getVersion();
    }
}