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

import java.util.ResourceBundle;

public class Scraper {

    private static UserAgent userAgent = new UserAgent(
            "gameDealScraper",
            "com.akassis.gamedealscraper",
            "v0.1",
            "TheFlyingDharma"
    );
    private static ResourceBundle credentialsBundle = ResourceBundle.getBundle("credentials");

    public static void main(String[] args){
        ScrapeSubreddit("GameDeals");
//        ScrapeSubreddit("AndroidGameDeals");
    }

    public static void ScrapeSubreddit(String targetSub){
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
                String dealPlatform = tokens[1];
                String dealTitle = tokens[2].trim();

                // Create a new deal object and populate it with relevant data
                Deal deal = new Deal(dealTitle, s.getUrl());
                deal.setPlatform(dealPlatform);
                deal.setSource("Reddit user " + s.getAuthor());
                deal.setSourceURL("https://www.reddit.com" + s.getPermalink());
                deal.setPostDate(s.getCreated());
                // Try to determine whether title is a full game or just a DLC
                if (title.contains("DLC")) {
                    deal.setType(DealTypes.DLC);
                }
                else {
                    deal.setType(DealTypes.FULL_GAME);
                }
                // TODO: Try to determine expiration date based on submission time + information in title, e.g. "Free for 48 hrs"
                String reFreeForDuration = ".*(?i)(free)[\\W]((for)[\\W][\\d*].*((hr)|(hour)|(day)|(week))).*";
                String reFreeUntilDate = ".*(?i)(free)[\\W](until)[\\W].*[\\d*].*";
                if (title.matches("")) {

                }

                System.out.println(deal.toString());
            }
        }
        System.out.println("---");
        System.out.print("/r/" + targetSub + ": " +
                "Found " + freeCount + " free titles out of " + viewCount + " submissions viewed.");
    }

}