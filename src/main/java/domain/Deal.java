package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import scraper.Convert;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Deal {
    private String       title;
    private URL          url;
    private String       store;
    private List<String> platforms;
    private LocalDate    datePosted;
    private LocalDate    dateExpires;
    private BigDecimal   normalPrice;
    private BigDecimal   currentPrice;
    private String       sourceName;
    private URL          sourceUrl;
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
    // TODO: Write constructors that will accept classes returned by scraper.WebScraper



    // Public Methods
    @Override
    public String toString() {
        // TODO: Expand output. StringBuilder or StringBuffer may be preferable here.
        // TODO: Rewrite using StringBuilder or StringBuffer. This is ugly and bad.
        String prefix = "";
        if (store != "" && store != null) {
            prefix = "[" + store + "] ";
        }
        String secondLine = "\n" + url.toString();

        return (prefix + title + secondLine);
    }

    public String toJsonString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    @JsonIgnore
    public Boolean isFree() {
        // TODO: Exception for missing data
        return currentPrice.intValue() == 0;
    }

    @JsonIgnore
    public Boolean isExpired() {
        // TODO: Exception for missing data
        return dateExpires.isBefore(LocalDate.now());
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

    public List<String> getPlatforms() {
        return platforms;
    }
    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }
    public void setPlatforms(String platforms) {
        List<String> l = new ArrayList<>();
        l.add(platforms);
        this.platforms = l;
    }

    public String getStore() {
        return store;
    }
    public void setStore(String store) {
        this.store = store;
    }
    public void setStore(List<String> storeList) {
        this.store = String.join("/", storeList);
    }


    public BigDecimal getNormalPrice() {
        return normalPrice;
    }
    public void setNormalPrice(BigDecimal normalPrice) {
        this.normalPrice = normalPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }
    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
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