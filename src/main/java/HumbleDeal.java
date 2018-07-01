import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HumbleDeal{
    public String       human_name;           // Title
    public String       human_url;            // URL (partial)
    public Long         sale_end;             // Expiry date in seconds from epoch
    public List<String> platforms;            // Platforms (OS)
    public List<String> delivery_methods;     // Platforms (DRM)
    public List<String> full_price;           // Normal price
    public List<String> current_price;        // Sale price
    public String       featured_image_small; // Image URL (full)

    private static String sourceUrl = "https://www.humblebundle.com/store/search?sort=discount&filter=onsale";
    private static String requestUrl = "https://www.humblebundle.com/store/api/search?sort=discount&filter=onsale&request=1&page_size=10";
    private static String baseUrl = "https://www.humblebundle.com/store/";

    public HumbleDeal() {
    }

    public Deal toDeal(){
        Deal d = new Deal();
        d.setSourceName("Humble Store");
        d.setSourceUrl(sourceUrl);

        d.setTitle(human_name);
        d.setUrl(baseUrl + human_url);
        d.setDateExpires(Convert.epochSecondsToLocalDate(sale_end));
        d.setPlatformOs(platforms);
        d.setPlatformDrm(delivery_methods);
        // TODO: Use a money lib to preserve currency code (Humble Store returns currency code for full_price and current_price at index 1)
        d.setNormalPrice(new BigDecimal(full_price.get(0)));
        d.setCurrentPrice(new BigDecimal(current_price.get(0)));
        d.setImageUrl(featured_image_small);

        return d;
    }

    private Boolean isFree() {
        return (Integer.valueOf(current_price.get(0)) == 0);
    }



    // Getters
    public static String getSourceUrl() {
        return sourceUrl;
    }

    public static String getRequestUrl() {
        return requestUrl;
    }

}