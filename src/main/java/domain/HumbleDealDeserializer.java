package domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import scraper.Convert;

import java.io.IOException;
import java.math.BigDecimal;

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
            throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        HumbleDeal hd = new HumbleDeal();
        hd.title = node.get("human_name").asText();
        hd.url = Convert.stringToURL(baseUrl + node.get("human_url").asText());
        hd.store = String.join("/", Convert.jsonElementToStringList(node, "delivery_methods"));
        hd.sourceName = sourceName;
        hd.sourceUrl = Convert.stringToURL(sourceUrl);
        hd.imageUrl = Convert.stringToURL(node.get("featured_image_small").asText());
        hd.platforms = Convert.jsonElementToStringList(node, "platforms");
        hd.normalPrice = BigDecimal.valueOf(node.get("full_price").get(0).asDouble());
        hd.currentPrice = BigDecimal.valueOf(node.get("current_price").get(0).asDouble());
        hd.expiry = (Convert.epochSecondsToLocalDate(node.get("sale_end").asLong()));

        return hd;
    }

}