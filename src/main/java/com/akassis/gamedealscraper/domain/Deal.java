package com.akassis.gamedealscraper.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

public abstract class Deal {
    // All deals should be able to implement these fields as a minimum
    String       title;
    URL          url;
    String       store;
    String       sourceName;
    URL          sourceUrl;
    // These fields may not be available from all sources and should therefore be considered optional
    URL          imageUrl;
    List<String> platforms;
    BigDecimal   normalPrice;
    BigDecimal   currentPrice;
    LocalDate    expiry;

    Deal() {
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        if (!Strings.isEmpty(store)) {
            s.append("[").append(store).append("] ");
        }
        s.append(title).append("\n");
        s.append(url.toString()).append("\n");

        return s.toString();
    }

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

    // Getters must be present for Jackson serialization
    public String getTitle() {
        return title;
    }
    public URL getUrl() {
        return url;
    }
    public String getStore() {
        return store;
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