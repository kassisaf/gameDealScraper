import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HumbleResponse {
    @JsonProperty("results")
    private List<HumbleDeal> results;

    public HumbleDeal get(int index){
        return this.results.get(index);
    }
}