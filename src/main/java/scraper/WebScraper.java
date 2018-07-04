package scraper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import domain.HumbleDeal;
import domain.HumbleResponse;

import java.io.IOException;

public abstract class WebScraper {
    // ObjectMapper interface to allow Unirest to serialize JSON responses directly to our custom objects
    private static void createObjectMapper() {
        // TODO: Check to see if we've already created an object mapper before making a new one
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

    public static HumbleResponse scrapeHumbleStore() {
        createObjectMapper();
        try {
            HttpResponse<HumbleResponse> response = Unirest.get(HumbleDeal.getRequestUrl()).asObject(HumbleResponse.class);
            HumbleResponse deals = response.getBody();
            return deals;
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }
}
