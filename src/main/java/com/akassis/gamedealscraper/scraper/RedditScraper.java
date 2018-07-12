package com.akassis.gamedealscraper.scraper;

import com.akassis.gamedealscraper.domain.Deal;
import com.akassis.gamedealscraper.domain.RedditDeal;
import com.akassis.gamedealscraper.utils.Logger;
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

abstract class RedditScraper {
    private static ResourceBundle credentialsBundle = ResourceBundle.getBundle("credentials");
    private static String username = credentialsBundle.getString("reddit_username");
    private static String password = credentialsBundle.getString("reddit_password");
    private static String clientId = credentialsBundle.getString("reddit_client_id");
    private static String clientSecret = credentialsBundle.getString("reddit_client_secret");

    private static UserAgent userAgent = new UserAgent(
            "web",
            Logger.getAppId(),
            Logger.getVersion(),
            username
    );

    static List<Deal> scrapeSubreddit(String targetSub, int numOfPosts){
        if (numOfPosts > 100) {
            numOfPosts = 100;
        }
        else if (numOfPosts < 1) {
            numOfPosts = 1;
        }

        Logger.println("Getting top " + numOfPosts + " submissions from /r/" + targetSub);
        Logger.println("Sending " + userAgent.toString());

        // Set up our reddit connection and auth
        Credentials credentialsReddit = Credentials.script(username, password, clientId, clientSecret);
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
