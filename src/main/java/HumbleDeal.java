import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HumbleDeal{
    @JsonProperty       ("human_name")
    public String        title;
    @JsonProperty       ("human_url")
    public String        url;
    @JsonProperty       ("sale_end")
    public String        dateExpires;
    @JsonProperty       ("platforms")
    public List<String>  platformOs;
    @JsonProperty       ("delivery_methods")
    public List<String>  platformDrm;
    @JsonProperty       ("full_price")
    public List<String>  priceNormal;
    @JsonProperty       ("current_price")
    public List<String>  priceCurrent;
    @JsonProperty       ("featured_image_small")
    public String        imageUrl;

    public HumbleDeal() {
        super();
    }

    private Deal toDeal(){
        Deal d = new Deal();
        // TODO: populate properties
        return d;
    }

}