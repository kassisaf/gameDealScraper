package com.akassis.gamedealscraper.scraper;

import com.akassis.gamedealscraper.domain.Deal;
import com.akassis.gamedealscraper.domain.HumbleDeal;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

abstract class WebScraper {

    static List<Deal> scrapeHumbleStore() {
        try {
            // Send Http request for Humble Store deals page and store it as a JsonNode
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(HumbleDeal.getRequestUrl())
                    .get()
                    .build();
            Response response = client.newCall(request).execute();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.body().string()).get("results");

            // Deserialize the JsonNode to a List<Deal>
            ObjectReader reader = mapper.readerFor(new TypeReference<List<HumbleDeal>>() {
            });
            List<Deal> deals = reader.readValue(node);
            return deals;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
