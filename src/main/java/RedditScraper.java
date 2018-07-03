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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class RedditScraper {

    private static UserAgent userAgent = new UserAgent(
            "gameDealScraper",
            "com.akassis.gamedealscraper",
            "v0.1",
            "TheFlyingDharma"
    );
    private static ResourceBundle credentialsBundle = ResourceBundle.getBundle("credentials");

    public static void main(String[] args) {
        scrapeSubreddit("GameDeals");
        //ScrapeSubreddit("AndroidGameDeals");
    }

    private static Boolean isFree(String title) {
        // This regex pattern looks for "free", but tries to avoid hitting games with "free" in the title,
        // e.g. "Freedom Fighters", or non-free games containing a "free gift" or a "free weekend".
        String reFreeGame = ".*(?i)[\\W](free)(?!( gift)|( weekend))[\\W].*";
        // Filter out "buy 2 get 1" (Gamestop spam)
        String reBuyOneGetOne = ".*(?i)(buy) [\\d] (get).*";
        return (title.matches(reFreeGame) && !title.matches(reBuyOneGetOne));
    }

    private static List<String> parseSubmissionTitle(String title) {

        return null;
    }

    public static void scrapeSubreddit(String targetSub){
        // TODO: rewrite to return output instead of printing
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

        for (Submission s : submissions) {

            String title = s.getTitle();
            if (isFree(title)){

                // Parse submission title into data we care about
                // Note: This split relies on user submissions following rule #3 title formatting:
                // https://www.reddit.com/r/GameDeals/wiki/rules#wiki_3._title_format
                String reTitleDelimiters = "[\\[\\]()]+";
                List<String> parsedTitle = new ArrayList<>(Arrays.asList(title.split(reTitleDelimiters)));
                parsedTitle.removeAll(Arrays.asList("", null));

                // Handle submissions that don't split cleanly into 3+ elements
                // TODO: Clean this up, catch out of bounds exception
                String storeName = "";
                String dealInfo;
                int tokens = parsedTitle.size();
                if (tokens >= 3) {
                    storeName = parsedTitle.get(0).trim();
                    dealInfo = parsedTitle.get(1).trim();
                }
                else {
                    dealInfo = title;
                }

                // Create a new deal object and populate it with relevant data
                Deal deal = new Deal(dealInfo, s.getUrl());
                deal.setStore(storeName);
                deal.setSourceName("Reddit (/u/" + s.getAuthor() + " via /r/" + targetSub + ")");
                deal.setSourceUrl("https://www.reddit.com" + s.getPermalink());
                deal.setDatePosted(Convert.dateToLocalDate(s.getCreated()));
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
    }

}
