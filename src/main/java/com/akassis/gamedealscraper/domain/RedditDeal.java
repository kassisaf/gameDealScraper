package com.akassis.gamedealscraper.domain;

import com.akassis.gamedealscraper.scraper.Convert;
import net.dean.jraw.models.Submission;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedditDeal extends Deal {
    private final Submission submission;
    private final String rawTitle;

    public RedditDeal(Submission submission) {
        super();
        this.submission = submission;
        rawTitle = submission.getTitle();
        parseSubmissionTitle(); // Updates title field, and store field if submission title can be parsed cleanly
        url = Convert.stringToURL(submission.getUrl());
        sourceName = "Reddit (/u/" + submission.getAuthor() + " via /r/" + submission.getSubreddit() + ")";
        sourceUrl = Convert.stringToURL("https://www.reddit.com" + submission.getPermalink());
        if (isFree()) {
            currentPrice = BigDecimal.valueOf(0);
        }
    }

    @Override
    public Boolean isFree() {
        // This regex pattern looks for "free", but tries to avoid hitting games with "free" in the title,
        // or non-free games that are DRM-free or include a "free gift," "free weekend," or "free shipping"
        String reFreeGame = ".*(?i)[\\W](?<!(drm.))(free)(?!( gift)|( weekend)|( shipping))[\\W].*";
        // Filter out "buy x get y free"
        String reBuyOneGetOne = ".*(?i)((buy)|(purchase)) .*( get ).*( free).*";

        return (rawTitle.matches(reFreeGame) && !rawTitle.matches(reBuyOneGetOne));
    }

    private void parseSubmissionTitle()
    {
        // Attempt to parse the submission title into useful info
        // Note: This split relies on user submissions following /r/GameDeals rule #3 title formatting:
        // https://www.reddit.com/r/GameDeals/wiki/rules#wiki_3._title_format
        // TODO: Handle subreddit-specific delimiters

        String reTitleDelimiters = "[\\[\\]()]+";
        List<String> splitTitle = new ArrayList<>(Arrays.asList(rawTitle.split(reTitleDelimiters)));
        splitTitle.removeAll(Arrays.asList("", null));
        // If the submission title doesn't split cleanly into our expected number of elements, disregard split results.
        if (splitTitle.size() >= 3) {
            store = splitTitle.get(0).trim();
            title = splitTitle.get(1).trim();
        }
        else {
            title = rawTitle;
        }

        // TODO: Attempt to parse expiration date from submission title
        // These regex patterns may be useful:
        //String reFreeForDuration = ".*(?i)(free)[\\W]((for)[\\W][\\d*].*((hr)|(hour)|(day)|(week))).*";
        //String reFreeUntilDate = ".*(?i)(free)[\\W](until)[\\W].*[\\d*].*";
    }
}