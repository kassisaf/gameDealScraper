import scraper.*;
import domain.HumbleDeal;
import domain.HumbleResponse;
import domain.RedditDeal;

import java.util.List;

class TestScrapers {
    public static void main(String args[]) {

        // Scrape Humble Store
        HumbleResponse humbleDeals = WebScraper.scrapeHumbleStore();

        System.out.println("-----\nBegin Humble Store output\n-----");
        for (HumbleDeal d : humbleDeals) {
            if (d.isFree()){
                System.out.println(d.toDeal().toString());
            }
            else {
                System.out.println("\tSkipping non-free result: " + d.human_name);
            }
        }

        // Scrape reddit
        int count = 0;
        int countSkipped = 0;
        List<RedditDeal> redditDeals = RedditScraper.scrapeSubreddit("GameDeals");

        System.out.println("-----\nBegin Reddit output\n-----");
        for (RedditDeal d : redditDeals) {
            count++;
            if (d.isFree()) {
                System.out.println(d.toDeal().toString());
            }
            else {
                countSkipped++;
                //System.out.println("\tSkipping non-free result: " + d.getRawTitle());
            }
        }
        System.out.println("\t Omitted " +
                countSkipped +
                " skipped, non-free submissions out of " +
                count +
                " checked.");

    }
}
