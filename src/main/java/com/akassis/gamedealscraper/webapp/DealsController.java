package com.akassis.gamedealscraper.webapp;

import com.akassis.gamedealscraper.domain.Deal;
import com.akassis.gamedealscraper.scraper.Scraper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DealsController {

    @RequestMapping(value = "/")
    public String deals(Model model) {

        List<Deal> deals = new ArrayList<>();

        List<Deal> humbleDeals = Scraper.scrapeHumbleStore();
        deals.addAll(humbleDeals);

        List<Deal> redditDeals = Scraper.scrapeSubreddit("GameDeals");
        redditDeals = Scraper.getFreeResults(redditDeals);
        deals.addAll(redditDeals);

//        deals = Scraper.getFreeResults(deals);

        model.addAttribute("deals", deals);
        model.addAttribute("iconMap", Deal.getIconMap());
        return "base";
    }
}
