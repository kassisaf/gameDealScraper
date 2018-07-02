import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;

public class WebScraper {
    public static void main(String[] args) {
        createObjectMapper();

        try {
            scrapeHumbleStore();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ObjectMapper interface to allow Unirest to serialize JSON responses directly to our custom objects
    private static void createObjectMapper() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private static void scrapeHumbleStore() throws UnirestException {
        HttpResponse<HumbleResponse> response = Unirest.get(HumbleDeal.getRequestUrl()).asObject(HumbleResponse.class);
        HumbleResponse deals = response.getBody();

        System.out.print("BREAKPOINT: scrapeHumbleStore() finished"); // TODO: Remove this
    }
}
