package domain;

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
    // These fields may not be available from all sources and are therefore optional
    LocalDate    expiry;
    List<String> platforms;
    BigDecimal   normalPrice;
    BigDecimal   currentPrice;
    URL          imageUrl;

    Deal() {
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        if (!Strings.isEmpty(store)) {
            s.append("[").append(store).append("] ");
        }
        s.append(title);
        s.append("\n").append(url.toString());

        return s.toString();
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    public Boolean isFree() {
        return currentPrice.equals(BigDecimal.valueOf(0));
    }

    public Boolean isExpired() {
        return expiry.isBefore(LocalDate.now());
    }

    public String getTitle() {
        return title;
    }

    public URL getUrl() {
        return url;
    }
}