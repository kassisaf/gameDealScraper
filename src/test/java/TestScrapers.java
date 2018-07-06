import domain.*;
import scraper.*;

import java.util.ArrayList;
import java.util.List;

class TestScrapers {
    public static void main(String args[]) {
//        testScrapeHumbleStore();
//        testScrapeReddit();
//        testDeserializeToDeal();
        testSerialize();
    }

    private static List<Deal> testScrapeHumbleStore() {
        List<Deal> humbleDeals = WebScraper.scrapeHumbleStore();

        System.out.println("-----\nBegin Humble Store output\n-----");
        for (Deal d : humbleDeals) {
            if (d.isFree()){
                System.out.println(d.toString());
            }
            else {
                System.out.println("\tSkipping non-free result: " + d.getTitle());
            }
        }
        return humbleDeals;
    }

    private static List<Deal> testScrapeReddit() {
        int count = 0;
        int countSkipped = 0;
        List<Deal> redditDeals = RedditScraper.scrapeSubreddit("GameDeals", 10);

        System.out.println("-----\nBegin Reddit output\n-----");
        for (Deal d : redditDeals) {
            count++;
            if (d.isFree()) {
                System.out.println(d.toString());
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
        return redditDeals;
    }

    private static List<Deal> testDeserializeToDeal() {
        List<Deal> redditDeals = testScrapeReddit();
        List<Deal> humbleDeals = testScrapeHumbleStore();

        List<Deal> allDeals = new ArrayList<>();
        allDeals.addAll(redditDeals);
        allDeals.addAll(humbleDeals);

        for (Deal d : allDeals) {
            if (d.isFree()) {
                System.out.println(d.toString());
            }
        }

        return allDeals;
    }

    private static void testSerialize() {
        List<Deal> deals = testDeserializeToDeal();

        for (Deal d : deals) {
            System.out.println(d.toJson());
        }
    }
}
