package com.akassis.gamedealscraper.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class Deal {
    // All deals should be able to implement these fields as a minimum
    String       title;
    URL          url;
    List<String> vendors;
    String       sourceName;
    URL          sourceUrl;
    // These fields may not be available from all sources and should therefore be considered optional
    URL          imageUrl;
    List<String> platforms;
    BigDecimal   normalPrice;
    BigDecimal   currentPrice;
    LocalDate    expiry;
    // Static map for translating store and platform strings to their corresponding FontAwesome <i> tag classes
    private static Map<String, String> iconMap = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
    static {
        // Vendors
        iconMap.put("download",    "fas fa-unlock-alt"); // TODO: Find a better icon for this (DRM-free)
        iconMap.put("steam",       "fab fa-steam");
        iconMap.put("twitch",      "fab fa-twitch");
        iconMap.put("google-play", "fab fa-google-play");
        iconMap.put("ios",         "fab fa-app-store-ios");
        // Platforms
        iconMap.put("windows",     "fab fa-windows");
        iconMap.put("mac",         "fab fa-apple");
        iconMap.put("linux",       "fab fa-linux");
        iconMap.put("xbox",        "fab fa-xbox");
        iconMap.put("playstation", "fab fa-playstation");
        iconMap.put("switch",      "fab fa-nintendo-switch");
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        if (vendors != null) {
            s.append("[").append(String.join("/", vendors)).append("] ");
        }
        s.append(title).append("\n");
        s.append(url.toString()).append("\n");

        return s.toString();
    }

    // Serialization is unnecessary with the current frontend, but leaving this here in case that changes in the future.
    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @JsonIgnore
    public Boolean isFree() {
        return currentPrice.equals(BigDecimal.valueOf(0));
    }

    @JsonIgnore
    public Boolean isExpired() {
        if (expiry != null) {
            return expiry.isBefore(LocalDate.now());
        }
        return false;
    }

    @JsonIgnore
    public static Map<String, String> getIconMap() {
        return iconMap;
    }

    // Getters must be present for Jackson serialization
    public String getTitle() {
        return title;
    }
    public URL getUrl() {
        return url;
    }
    public List<String> getVendors() {
        return vendors;
    }
    public String getSourceName() {
        return sourceName;
    }
    public URL getSourceUrl() {
        return sourceUrl;
    }
    public URL getImageUrl() {
        return imageUrl;
    }
    public List<String> getPlatforms() {
        return platforms;
    }
    public BigDecimal getNormalPrice() {
        return normalPrice;
    }
    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }
    public String getExpiry() {
        if (expiry != null) {
            return expiry.toString();
        }
        return null;
    }
}