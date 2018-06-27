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
import java.util.regex.Pattern;

public class Scraper {

    private static UserAgent userAgent = new UserAgent(
            "gameDealScraper",
            "com.akassis.gamedealscraper",
            "v0.1",
            "TheFlyingDharma"
    );
    private static ResourceBundle credentialsBundle = ResourceBundle.getBundle("credentials");

    public static void main(String[] args){

        // Set up our reddit connection and auth
        Credentials credentialsReddit = Credentials.script(
                credentialsBundle.getString("reddit_username"),
                credentialsBundle.getString("reddit_password"),
                credentialsBundle.getString("reddit_client_id"),
                credentialsBundle.getString("reddit_client_secret")
        );
        NetworkAdapter adapter = new OkHttpNetworkAdapter(userAgent);
        RedditClient reddit = OAuthHelper.automatic(adapter, credentialsReddit);

        // Get submissions from the subs we care about
        // TODO: iterate over a list of relevant subs to get around 100 submission limit
        DefaultPaginator<Submission> dealSubs = reddit.subreddits("GameDeals", "AndroidGameDeals").posts()
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
            // This regex pattern looks for "free", but tries to avoid hitting games with "free" in the title
            // e.g. "Freedom", or posts containing a "free gift for redditors" when not actually free.
            String pattern = ".*(?i)[\\W](free)(?!( gift))[\\W].*";
            if (title.toLowerCase().matches(pattern)){

//            if (title.toLowerCase().contains("(free")){
                freeCount++;
                //TODO: construct a json object containing platform, title, post url, store url, submitter, and expiration date if found


                System.out.println(title);
            }
        }
        System.out.println("---");
        System.out.print("Found " + freeCount + " free titles out of " + viewCount + " submissions viewed.");

    }

}