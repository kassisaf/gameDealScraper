import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;

public class WebScraper {
    public static void main(String[] args) {
        CreateObjectMapper();

        try {
            ScrapeHumbleStore();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void CreateObjectMapper() {
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

    private static void ScrapeHumbleStore() throws UnirestException {
        HttpResponse<HumbleResponse> response = Unirest.get(HumbleDeal.getRequestUrl()).asObject(HumbleResponse.class);
        HumbleResponse deals = response.getBody();

        System.out.print("BREAKPOINT: ScrapeHumbleStore() finished"); // TODO: Remove this
    }
}