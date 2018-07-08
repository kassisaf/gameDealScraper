package com.akassis.gamedealscraper.scraper;

import com.akassis.gamedealscraper.domain.Deal;

import java.util.ArrayList;
import java.util.List;

public abstract class Scraper {

    public static List<Deal> scrapeSubreddit(String targetSub) {
        return scrapeSubreddit(targetSub, 100);
    }
    public static List<Deal> scrapeSubreddit(String targetSub, int numOfPosts) {
        System.out.println(getTimeStamp() + "  Scraping /r/" + targetSub + "...");
        List<Deal> deals = RedditScraper.scrapeSubreddit(targetSub, numOfPosts);
        System.out.println(getTimeStamp() + "  Returning " + deals.size() + " results.");
        return deals;
    }

    public static List<Deal> scrapeHumbleStore() {
        System.out.println(getTimeStamp() + "  Scraping Humble Store...");
        List<Deal> deals = WebScraper.scrapeHumbleStore();
        System.out.println(getTimeStamp() + "  Returning " + deals.size() + " results.");
        return WebScraper.scrapeHumbleStore();
    }

    public static List<Deal> getFreeResults(List<Deal> deals) {
        List<Deal> freebies = new ArrayList<>();
        for (Deal d : deals) {
            if (d.isFree()) {
                freebies.add(d);
            }
        }
        return freebies;
    }

    private static String getTimeStamp(){
        return new java.sql.Timestamp(System.currentTimeMillis()).toString();
    }

}
