import java.util.Date;

public class Deal {
    // Mandatory fields
    private String Title;
    private String StoreURL;
    // Optional fields
    private String Platform; // Relies on user-submitted data. May indicate either hardware platform or DRM platform.
    private String Source;
    private String SourceURL;
    private Date PostDate;
    private Date ExpirationDate;
    private DealTypes Type;

    public Deal(String title, String storeURL) {
        Title = title;
        StoreURL = storeURL;
    }

    @Override
    public String toString() {
        // Returns a two-line string.
        // First line will contain platform, title, and time until expiration (if known).
        // Second line will contain the store URL.
        String dealString = "";

        if (Platform != null) {
            dealString += "[" + Platform + "] ";
        }

        dealString += Title;

        // TODO: Implement expiration date here using appropriate date/time object type
//        if (ExpirationDate != null) {
//            // TODO: determine time remaining until deal expires and concatenate it to dealString
//        }

        dealString += "\n" + StoreURL;
        return dealString;
    }

    // Getters & Setters

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getStoreURL() {
        return StoreURL;
    }

    public void setStoreURL(String storeURL) {
        StoreURL = storeURL;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getSourceURL() {
        return SourceURL;
    }

    public void setSourceURL(String sourceURL) {
        SourceURL = sourceURL;
    }

    public Date getPostDate() {
        return PostDate;
    }

    public void setPostDate(Date postDate) {
        PostDate = postDate;
    }

    public Date getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        ExpirationDate = expirationDate;
    }

    public DealTypes getType() {
        return Type;
    }

    public void setType(DealTypes type) {
        Type = type;
    }

    public String getPlatform() {
        return Platform;
    }

    public void setPlatform(String platform) {
        Platform = platform;
    }
}