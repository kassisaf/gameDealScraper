import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Deal {
    @JsonProperty("human_name")
    private String       title;
    @JsonProperty("human_url")
    private URL          url;
    private Date         datePosted;
    @JsonProperty("sale_end")
    private Date         dateExpires;
    @JsonProperty("platforms")
    private String[]     platformOs;
    @JsonProperty("delivery_methods")
    private String[]     platformDrm;
    @JsonProperty("full_price")
    private BigDecimal   priceNormal;
    @JsonProperty("current_price")
    private BigDecimal   priceCurrent;
    private String       sourceName;
    private URL          sourceUrl;
    private ContentType  contentType;
    @JsonProperty("featured_image_small")
    private URL          imageUrl;

    // Constructors
    public Deal() {
    }
    public Deal(String title, URL url) {
        this.setTitle(title);
        this.setUrl(url);
    }
    public Deal(String title, String url) {
        this.setTitle(title);
        this.setUrl(url);
    }



    // Public Methods
    @Override
    public String toString() {
        String prefix = "";
        if (platformDrm != null) {
            prefix = "[" + String.join(", ", platformDrm) + "] ";
        }
        String secondLine = "\n" + url.toString();

        return (prefix + title + secondLine);
    }

    public Boolean IsFree() {
        // TODO: Exception for missing data
        return priceCurrent.intValue() == 0;
    }

    public Boolean IsExpired() {
        // TODO: Exception for missing data
        return dateExpires.before(new Date());
    }



    // Private Methods
    private static URL StringToURL(String stringUrl) {
        try {
            URL url = new URL(stringUrl);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }



    // Getters & Setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public URL getUrl() {
        return url;
    }
    public void setUrl(URL url) {
        this.url = url;
    }
    public void setUrl(String url) {
        this.url = StringToURL(url);
    }

    public Date getDatePosted() {
        return datePosted;
    }
    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }
    // TODO: Add setters for Date variables that accept and parse a String representation (epoch/NLP)

    public Date getDateExpires() {
        return dateExpires;
    }
    public void setDateExpires(Date dateExpires) {
        this.dateExpires = dateExpires;
    }

    public String[] getPlatformOs() {
        return platformOs;
    }
    public void setPlatformOs(String[] platformOs) {
        this.platformOs = platformOs;
    }
    public void setPlatformOs(String platformOs) {
        this.platformOs = new String[]{platformOs};
    }


    public String[] getPlatformDrm() {
        return platformDrm;
    }
    public void setPlatformDrm(String[] platformDrm) {
        this.platformDrm = platformDrm;
    }
    public void setPlatformDrm(String platformDrm) {
        this.platformDrm = new String[]{platformDrm};
    }

    public BigDecimal getPriceNormal() {
        return priceNormal;
    }
    public void setPriceNormal(BigDecimal priceNormal) {
        this.priceNormal = priceNormal;
    }


    public BigDecimal getPriceCurrent() {
        return priceCurrent;
    }
    public void setPriceCurrent(BigDecimal priceCurrent) {
        this.priceCurrent = priceCurrent;
    }
    public void setPriceCurrent(String priceCurrent) {
        if (priceCurrent.toLowerCase().trim().equals("free")) {
            this.priceCurrent = new BigDecimal(0);
        }
        else {
            throw new RuntimeException("Invalid price: " + priceCurrent);
        }
    }

    public String getSourceName() {
        return sourceName;
    }
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public URL getSourceUrl() {
        return sourceUrl;
    }
    public void setSourceUrl(URL sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = StringToURL(sourceUrl);
    }

    public ContentType getContentType() {
        return contentType;
    }
    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public URL getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(URL imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = StringToURL(imageUrl);
    }
}