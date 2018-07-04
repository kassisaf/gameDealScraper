package domain;

import net.dean.jraw.models.Submission;
import scraper.Convert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedditDeal {
    private Submission submission;
    private String rawTitle;
    private String parsedTitle;
    private String parsedStore;

    public RedditDeal(Submission submission) {
        this.submission = submission;
        rawTitle = submission.getTitle();
        // Attempt to parse the submission title into useful info
        // Note: This split relies on user submissions following /r/GameDeals rule #3 title formatting:
        // https://www.reddit.com/r/GameDeals/wiki/rules#wiki_3._title_format
        // TODO: Handle subreddit-specific delimiters
        String reTitleDelimiters = "[\\[\\]()]+";
        List<String> splitTitle = new ArrayList<>(Arrays.asList(rawTitle.split(reTitleDelimiters)));
        splitTitle.removeAll(Arrays.asList("", null));
        // If the submission title doesn't split cleanly into our expected number of elements, disregard split results.
        if (splitTitle.size() >= 3) {
            parsedStore = splitTitle.get(0).trim();
            parsedTitle = splitTitle.get(1).trim();
        }
        else {
            parsedTitle = rawTitle;
        }
        // TODO: Attempt to parse expiration date from submission title
        // These regex patterns may be useful:
        //String reFreeForDuration = ".*(?i)(free)[\\W]((for)[\\W][\\d*].*((hr)|(hour)|(day)|(week))).*";
        //String reFreeUntilDate = ".*(?i)(free)[\\W](until)[\\W].*[\\d*].*";
    }

    public Deal toDeal() {
        Deal deal = new Deal(parsedTitle, submission.getUrl());
        if (parsedStore != null) {
            deal.setStore(parsedStore);
        }
        deal.setSourceName("Reddit (/u/" + submission.getAuthor() + " via /r/" + submission.getSubreddit() + ")");
        deal.setSourceUrl("https://www.reddit.com" + submission.getPermalink());
        deal.setDatePosted(Convert.dateToLocalDate(submission.getCreated()));
        return deal;
    }

    public Boolean isFree() {
        // This regex pattern looks for "free", but tries to avoid hitting games with "free" in the rawTitle,
        // e.g. "Freedom Fighters", or non-free games containing a "free gift" or a "free weekend".
        String reFreeGame = ".*(?i)[\\W](free)(?!( gift)|( weekend))[\\W].*";
        // Filter out "buy 2 get 1" (Gamestop spam)
        String reBuyOneGetOne = ".*(?i)(buy) [\\d] (get).*";
        return (rawTitle.matches(reFreeGame) && !rawTitle.matches(reBuyOneGetOne));
    }
}