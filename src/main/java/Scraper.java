import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.DefaultPaginator;

import java.io.IOException;
import java.util.ResourceBundle;

public class Scraper {

    private static UserAgent userAgent = new UserAgent(
            "gameDealScraper",
            "com.akassis.gamedealscraper",
            "v0.1",
            "TheFlyingDharma"
    );
    private static ResourceBundle credentialsBundle = ResourceBundle.getBundle("credentials");

    public static void main(String[] args) {
        try {
            ScrapeHumbleStore();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        //ScrapeSubreddit("GameDeals");
        //ScrapeSubreddit("AndroidGameDeals");
    }

    private static void ScrapeSubreddit(String targetSub){
        // Set up our reddit connection and auth
        Credentials credentialsReddit = Credentials.script(
                credentialsBundle.getString("reddit_username"),
                credentialsBundle.getString("reddit_password"),
                credentialsBundle.getString("reddit_client_id"),
                credentialsBundle.getString("reddit_client_secret")
        );
        NetworkAdapter adapter = new OkHttpNetworkAdapter(userAgent);
        RedditClient reddit = OAuthHelper.automatic(adapter, credentialsReddit);

        // Get 100 most recent submissions from the target subreddit
        DefaultPaginator<Submission> dealSubs = reddit.subreddit(targetSub).posts()
                .sorting(SubredditSort.NEW)
                .timePeriod(TimePeriod.MONTH)
                .limit(100)
                .build();

        Listing<Submission> submissions = dealSubs.next();

        Integer viewCount = 0;
        Integer freeCount = 0;
        for (Submission s : submissions) {
            viewCount++;

            String title = s.getTitle();
            // This regex pattern looks for "free", but tries to avoid hitting games with "free" in the title,
            // e.g. "Freedom Fighters", or non-free games containing a "free gift".
            String reFreeGame = ".*(?i)[\\W](free)(?!( gift))[\\W].*";
            if (title.toLowerCase().matches(reFreeGame)){
                freeCount++;

                // Parse submission title into data we care about
                String reTitleDelimiters = "[\\[\\]()]+";
                String[] tokens = title.split(reTitleDelimiters);
                // TODO: Add exception handling for submissions that don't split cleanly into 3+ elements
                // Note: This split relies on user submissions following rule #3:
                // https://www.reddit.com/r/GameDeals/wiki/rules#wiki_3._title_format
                String storeName = tokens[1];
                String dealInfo = tokens[2].trim();

                // Create a new deal object and populate it with relevant data
                Deal deal = new Deal(dealInfo, s.getUrl());
                deal.setPlatformDrm(storeName);
                deal.setSourceName("Reddit (/u/" + s.getAuthor() + " via /r/" + targetSub + ")");
                deal.setSourceUrl("https://www.reddit.com" + s.getPermalink());
                deal.setDatePosted(s.getCreated());
                // Try to determine whether title is a full game or just a DLC
                if (title.contains("DLC")) {
                    deal.setContentType(ContentType.DLC);
                }
                else {
                    deal.setContentType(ContentType.GAME);
                }
                // TODO: Try to determine expiration date based on submission time + information in title, e.g. "Free for 48 hrs"
                String reFreeForDuration = ".*(?i)(free)[\\W]((for)[\\W][\\d*].*((hr)|(hour)|(day)|(week))).*";
                String reFreeUntilDate = ".*(?i)(free)[\\W](until)[\\W].*[\\d*].*";
                if (title.matches(reFreeUntilDate)) {
                    // TODO: Parse date following "free until" and set expiration
                }
                else if (title.matches(reFreeForDuration)) {
                    // TODO: Parse duration following "free for" and set expiration
                }

                System.out.println(deal.toString());
            }
        }
        System.out.println("---");
        System.out.print("/r/" + targetSub + ": " +
                "Found " + freeCount + " free titles out of " + viewCount + " submissions viewed.");
    }

    private static void ScrapeSteam(){
        // TODO: Implement scraping for Steam sales
        //String humanURL = "https://store.steampowered.com/search/?specials=1";
    }

    private static void ScrapeHumbleStore() throws UnirestException {
        // TODO: Implement scraping for Humble Store
        String humanURL = "https://www.humblebundle.com/store/search?sort=discount&filter=onsale";
        String requestURL = "https://www.humblebundle.com/store/api/search?sort=discount&filter=onsale&request=1&page_size=20";

        // TODO: Serialize response to JSON object and extract the data we care about

//        HttpResponse<String> response = Unirest.get(requestURL)
//                .header("Cache-Control", "no-cache")
//                .header("Postman-Token", "746fad60-b000-40b2-9a7e-0c5d50b67795")
//                .asString();
//        final JSONObject obj = new JSONObject(response);
//        final JSONArray deals = new JSONObject(obj.getString("body")).getJSONArray("results");

        // Unirest to Jackson ObjectMapper Interface
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

        HttpResponse<Deal> dealResponse = Unirest.get(requestURL).asObject(Deal.class);
        Deal deal = dealResponse.getBody();


        System.out.print("BREAKPOINT: ScrapeHumbleStore() finished"); // TODO: Remove this
    }

}