package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import scraper.Convert;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HumbleDeal extends Deal {
    public String       human_name;           // Title
    public String       human_url;            // URL (partial)
    public Long         sale_end;             // Expiry date in seconds from epoch
    public List<String> platforms;            // Platforms (OS)
    public List<String> delivery_methods;     // Usually this is a list containing only "Steam"
    public List<String> full_price;           // Normal price
    public List<String> current_price;        // Sale price
    public String       featured_image_small; // Image URL (full)

    private static final String sourceUrl = "https://www.humblebundle.com/store/search?sort=discount&filter=onsale";
    // TODO: Extract page_size to a parser argument
    private static final String requestUrl = "https://www.humblebundle.com/store/api/search?sort=discount&filter=onsale&request=1&page_size=10";
    private static final String baseUrl = "https://www.humblebundle.com/store/";

    public HumbleDeal() {
    }

//    @JsonCreator
//    public HumbleDeal(
//            @JsonProperty String       human_name,
//            @JsonProperty String       human_url,
//            @JsonProperty Long         sale_end,
//            @JsonProperty List<String> platforms,
//            //@JsonProperty List<String> delivery_methods,
//            @JsonProperty List<String> full_price,
//            @JsonProperty List<String> current_price,
//            @JsonProperty("featured_image_small") String       featured_image_small) {
//
//        super();
//        // Mandatory fields
//        title = human_name;
//        url = Convert.stringToURL(baseUrl + human_url);
//        store = "Humble Store";
//        sourceName = "Humble Store";
//        super.sourceUrl = Convert.stringToURL(sourceUrl);
//        // Optional fields
//        expiry = Convert.epochSecondsToLocalDate(sale_end);
//        super.platforms = platforms;
//        // TODO: Use a money lib to preserve currency code (Humble Store returns currency code for full_price and current_price at index 1)
//        normalPrice = Convert.stringToBigDecimal(full_price.get(0));
//        currentPrice = Convert.stringToBigDecimal(current_price.get(0));
//        imageUrl = Convert.stringToURL(featured_image_small);
//    }

    @Override
    public Boolean isFree() {
        return Convert.stringToBigDecimal(current_price.get(0)).equals(BigDecimal.valueOf(0));
    }

    @Override
    public String getTitle() {
        return human_name;
    }

    public static String getRequestUrl() {
        return requestUrl;
    }

}