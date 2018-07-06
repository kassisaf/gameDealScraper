package scraper;

import domain.Deal;
import domain.RedditDeal;
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
import java.util.List;
import java.util.ResourceBundle;

public abstract class RedditScraper {

    private static UserAgent userAgent = new UserAgent(
            "gameDealScraper",
            "com.akassis.gamedealscraper",
            "v0.0.1-PRE",
            "TheFlyingDharma"
    );
    private static ResourceBundle credentialsBundle = ResourceBundle.getBundle("credentials");

    public static List<Deal> scrapeSubreddit(String targetSub) {
        return scrapeSubreddit(targetSub, 100);
    }

    public static List<Deal> scrapeSubreddit(String targetSub, int numOfPosts){
        if (numOfPosts > 100) {
            numOfPosts = 100;
        }
        else if (numOfPosts < 1) {
            numOfPosts = 1;
        }

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
                .limit(numOfPosts)
                .build();

        Listing<Submission> submissions = dealSubs.next();

        List<Deal> deals = new ArrayList<>();
        for (Submission s : submissions) {
            deals.add(new RedditDeal(s));
        }

        return deals;
    }

}
