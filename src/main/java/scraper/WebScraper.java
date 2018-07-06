package scraper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import domain.Deal;
import domain.HumbleDeal;

import java.io.IOException;
import java.util.List;

public abstract class WebScraper {

    public static List<Deal> scrapeHumbleStore() {
        try {
            // Send Http request for Humble Store deals page and store it as a JsonNode
            HttpResponse<com.mashape.unirest.http.JsonNode> response = Unirest.get(HumbleDeal.getRequestUrl()).asJson();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.getBody().toString()).get("results");
            // Deserialize the JsonNode to a List<Deal>
            ObjectReader reader = mapper.readerFor(new TypeReference<List<HumbleDeal>>() {
            });
            List<Deal> deals = reader.readValue(node);
            return deals;

        } catch (UnirestException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
