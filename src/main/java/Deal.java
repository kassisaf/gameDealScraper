import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Deal {
    private String        title;
    private URL           url;
    private LocalDate     datePosted;
    private LocalDate     dateExpires;
    private List<String>  platformOs;
    private List<String>  platformDrm;
    private BigDecimal    priceNormal;
    private BigDecimal    priceCurrent;
    private String        sourceName;
    private URL           sourceUrl;
    private ContentType   contentType;
    private URL           imageUrl;

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
        // TODO: Expand output. StringBuilder or StringBuffer may be preferable here.
        String prefix = "";
        if (platformDrm != null) {
            prefix = "[" + String.join(", ", platformDrm) + "] ";
        }
        String secondLine = "\n" + url.toString();

        return (prefix + title + secondLine);
    }

    public Boolean isFree() {
        // TODO: Exception for missing data
        return priceCurrent.intValue() == 0;
    }

    public Boolean isExpired() {
        // TODO: Exception for missing data
        return dateExpires.isBefore(LocalDate.now());
    }



    // Private Methods




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
        this.url = Convert.stringToURL(url);
    }

    public LocalDate getDatePosted() {
        return datePosted;
    }
    public void setDatePosted(LocalDate datePosted) {
        this.datePosted = datePosted;
    }
    // TODO: Add setters for Date variables that accept and parse a String representation (epoch/NLP)

    public LocalDate getDateExpires() {
        return dateExpires;
    }
    public void setDateExpires(LocalDate dateExpires) {
        this.dateExpires = dateExpires;
    }

    public List<String> getPlatformOs() {
        return platformOs;
    }
    public void setPlatformOs(List<String> platformOs) {
        this.platformOs = platformOs;
    }
    public void setPlatformOs(String platformOs) {
        List<String> l = new ArrayList<>();
        l.add(platformOs);
        this.platformOs = l;
    }

    public List<String> getPlatformDrm() {
        return platformDrm;
    }
    public void setPlatformDrm(List<String> platformDrm) {
        this.platformDrm = platformDrm;
    }
    public void setPlatformDrm(String platformDrm) {
        List<String> l = new ArrayList<>();
        l.add(platformDrm);
        this.platformDrm = l;
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
        this.sourceUrl = Convert.stringToURL(sourceUrl);
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
        this.imageUrl = Convert.stringToURL(imageUrl);
    }
}