package com.akassis.gamedealscraper.domain;

import com.akassis.gamedealscraper.utils.Convert;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class HumbleDealDeserializer extends StdDeserializer<HumbleDeal> {
    private static final String sourceName = "Humble Store";
    private static final String sourceUrl = "https://www.humblebundle.com/store/search?sort=discount&filter=onsale";
    private static final String baseUrl = "https://www.humblebundle.com/store/";

    public HumbleDealDeserializer() {
        this(null);
    }

    public HumbleDealDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public HumbleDeal deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        HumbleDeal hd = new HumbleDeal();
        hd.title = node.get("human_name").asText();
        hd.url = Convert.stringToURL(baseUrl + node.get("human_url").asText());

        List<String> vendors = Convert.jsonElementToStringList(node, "delivery_methods");
        vendors.sort(Comparator.reverseOrder());
        hd.vendors = vendors;

        hd.sourceName = sourceName;
        hd.sourceUrl = Convert.stringToURL(sourceUrl);

        try {
            String image = node.get("featured_image_small").asText();
            hd.imageUrl = Convert.stringToURL(image);
        } catch (NullPointerException e) {
            hd.imageUrl = null;
        }

        // Sort the list of platforms in reverse alphabetical order so that Windows > Mac > Linux
        List<String> platforms = Convert.jsonElementToStringList(node, "platforms");
        platforms.sort(Comparator.reverseOrder());
        hd.platforms = platforms;

        hd.normalPrice = BigDecimal.valueOf(node.get("full_price").get(0).asDouble());
        hd.currentPrice = BigDecimal.valueOf(node.get("current_price").get(0).asDouble());
        hd.expiry = (Convert.epochSecondsToLocalDate(node.get("sale_end").asLong()));

        return hd;
    }

}