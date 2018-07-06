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
        List<Deal> humbleDeals = ScraperController.scrapeHumbleStore();
//        System.out.println(ScraperController.getReport(humbleDeals, false));
        return humbleDeals;
    }

    private static List<Deal> testScrapeReddit() {
        List<Deal> redditDeals = ScraperController.scrapeSubreddit("GameDeals");
//        System.out.println(ScraperController.getReport(redditDeals));
        return redditDeals;
    }

    private static List<Deal> testDeserializeToDeal() {
        List<Deal> humbleDeals = testScrapeHumbleStore();
        List<Deal> redditDeals = testScrapeReddit();

        List<Deal> allDeals = new ArrayList<>();
        allDeals.addAll(redditDeals);
        allDeals.addAll(humbleDeals);

        System.out.println(ScraperController.getReport(allDeals));

        return allDeals;
    }

    private static void testSerialize() {
        List<Deal> deals = testDeserializeToDeal();

        for (Deal d : deals) {
            System.out.println(d.toJson());
        }
    }
}
