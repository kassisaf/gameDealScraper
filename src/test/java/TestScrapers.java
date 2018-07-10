import com.akassis.gamedealscraper.domain.Deal;
import com.akassis.gamedealscraper.scraper.Scraper;

import java.util.*;

class TestScrapers {
    public static void main(String args[]) {
//        testScrapeHumbleStore();
//        testScrapeReddit();
//        testDeserializeToDeal();
        testSerialize();
//        testTreeMap();
    }

    private static List<Deal> testScrapeHumbleStore() {
        List<Deal> humbleDeals = Scraper.scrapeHumbleStore();
        return humbleDeals;
    }

    private static List<Deal> testScrapeReddit() {
        List<Deal> redditDeals = Scraper.scrapeSubreddit("GameDeals");
        return redditDeals;
    }

    private static List<Deal> testDeserializeToDeal() {
        List<Deal> humbleDeals = testScrapeHumbleStore();
        List<Deal> redditDeals = testScrapeReddit();

        List<Deal> allDeals = new ArrayList<>();
        allDeals.addAll(redditDeals);
        allDeals.addAll(humbleDeals);

        return allDeals;
    }

    private static void testSerialize() {
        List<Deal> deals = Scraper.getFreeResults(testDeserializeToDeal());

        for (Deal d : deals) {
            System.out.println(d.toJson());
        }
    }

    private static void testTreeMap() {
        Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        List<String> outOfOrder = new ArrayList<>();
        outOfOrder.add("mac");
        outOfOrder.add("linux");
        outOfOrder.add("linux");
        outOfOrder.add("mac");
        outOfOrder.add("windows");
        outOfOrder.sort(Comparator.reverseOrder());

        for (String vendor : outOfOrder) {
            System.out.println(Deal.getIconMap().get(vendor));
        }

    }
}
