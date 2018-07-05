package domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import scraper.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public class HumbleDealDeserializer extends StdDeserializer<HumbleDeal> {

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
        hd.url = Convert.stringToURL(HumbleDeal.getBaseUrl() + node.get("human_url").asText());
        hd.store = String.join("/", node.get("delivery_methods").asText());
        hd.sourceName = "Humble Store";
//        hd.super.sourceUrl = HumbleDeal.getSourceUrl();
        hd.expiry = (Convert.epochSecondsToLocalDate(node.get("sale_end").asLong()));
        hd.platforms = null;
        hd.normalPrice = BigDecimal.valueOf(node.get("full_price").get(0).asDouble());
        hd.currentPrice = BigDecimal.valueOf(node.get("current_price").get(0).asDouble());
        hd.imageUrl = Convert.stringToURL(node.get("featured_image_small").asText());

        return hd;
    }
//
//    private List<String> nodeElementToList(JsonNode node, String element) {
//        Iterator<JsonNode> elements = node.elements();
//        for (; elements.hasNext();) {
//            String type = (String) elements.next().get(element).asText();
//
////            if (type.equals())
//        }
//    }
}
