package com.akassis.gamedealscraper.scraper;

import com.akassis.gamedealscraper.domain.Deal;
import com.akassis.gamedealscraper.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class Scraper {

    public static List<Deal> scrapeSubreddit(String targetSub) {
        return scrapeSubreddit(targetSub, 100);
    }
    public static List<Deal> scrapeSubreddit(String targetSub, int numOfPosts) {
        Logger.println("Scraping Reddit...");
        List<Deal> deals = RedditScraper.scrapeSubreddit(targetSub, numOfPosts);
        Logger.println("Returning " + deals.size() + " results.");
        return deals;
    }

    public static List<Deal> scrapeHumbleStore() {
        Logger.println("Scraping Humble Store...");
        List<Deal> deals = WebScraper.scrapeHumbleStore();
        Logger.println("Returning " + deals.size() + " results.");
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



}
