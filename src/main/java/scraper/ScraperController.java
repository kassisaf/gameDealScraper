package scraper;

import domain.Deal;

import java.util.ArrayList;
import java.util.List;

public abstract class ScraperController {

    public static List<Deal> scrapeSubreddit(String targetSub) {
        return scrapeSubreddit(targetSub, 100);
    }
    public static List<Deal> scrapeSubreddit(String targetSub, int numOfPosts) {
        return RedditScraper.scrapeSubreddit(targetSub, numOfPosts);
    }

    public static List<Deal> scrapeHumbleStore() {
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

    public static String getReport(List<Deal> deals){
        return getReport(deals, true);
    }
    public static String getReport(List<Deal> deals, boolean freeOnly) {
        List<Deal> freeDeals = getFreeResults(deals);
        int total = deals.size();
        int free = freeDeals.size();
        StringBuilder sb = new StringBuilder();
        String separator = "-----";

        sb.append(separator)
                .append("\n")
                .append("Begin report");

        if (freeOnly) {
            deals = getFreeResults(deals);
            sb.append(" (Showing FREE results only)");
        }
        else {
            sb.append(" (Showing ALL results)");
        }

        sb.append("\n")
                .append(separator)
                .append("\n");

        for (Deal d : deals) {
            if (freeOnly) {
                if (d.isFree()) {
                    sb.append(d.toString());
                }
            }
            else {
                sb.append(d.toString());
            }
        }

        sb.append(separator)
                .append("\n")
                .append("Found ")
                .append(free)
                .append(" free results out of ")
                .append(total)
                .append(" checked.\n");

        return sb.toString();
    }

}
