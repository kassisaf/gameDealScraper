import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HumbleDeal{
    public String        human_name;           // Title
    public String        human_url;            // URL (partial)
    public String        sale_end;             // Expiry date in seconds from epoch
    public List<String>  platforms;            // Platforms (OS)
    public List<String>  delivery_methods;     // Platforms (DRM)
    public List<String>  full_price;           // Normal price
    public List<String>  current_price;        // Sale price
    public String        featured_image_small; // Image URL (full)

    public HumbleDeal() {
        super();
    }

    private Deal toDeal(){
        Deal d = new Deal();
        // TODO: populate properties
        return d;
    }

}