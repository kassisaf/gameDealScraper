package com.akassis.gamedealscraper.webapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DealsController {

    @RequestMapping(value = "/")
    public String deals(HttpServletRequest request, Model model) {
        String name = request.getParameter("name");

        if (name == null) {
            name = "world";
        }
        name = null;

        model.addAttribute("name", name);
        return "base";
    }
}
