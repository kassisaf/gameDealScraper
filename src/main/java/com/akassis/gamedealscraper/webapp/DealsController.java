package com.akassis.gamedealscraper.webapp;

import com.akassis.gamedealscraper.domain.Deal;
import com.akassis.gamedealscraper.scraper.Scraper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class DealsController {

    @RequestMapping(value = "/")
    public String deals(Model model) {
//        List<Deal> deals = Scraper.scrapeHumbleStore();

        List<Deal> deals = Scraper.scrapeSubreddit("GameDeals");
        deals = Scraper.getFreeResults(deals);

        model.addAttribute("deals", deals);
        return "base";
    }
}
