import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public abstract class Convert {

    public static URL stringToURL(String stringUrl) {
        try {
            URL url = new URL(stringUrl);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LocalDate dateToLocalDate (Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDate epochSecondsToLocalDate(Long seconds) {
        return Instant.ofEpochSecond(seconds).atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
