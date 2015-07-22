package com.feedlyclone.web;

import com.feedlyclone.service.FeedMessageService;
import com.feedlyclone.service.FeedWorkerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FeedController {
    private static final Logger LOGGER = Logger.getLogger(FeedController.class.getSimpleName());

    @Autowired
    private FeedMessageService feedMessageService;

    @Autowired
    private FeedWorkerService feedWorkerService;

    @RequestMapping(value = "/addFeed")
    public String addFeed(@RequestParam("newFeedValue") String newFeedUrl, Model model){
        LOGGER.debug("add new feed: " + newFeedUrl);
        model.addAttribute("feedMessages", feedWorkerService.readFeedFromUrl(newFeedUrl));
        model.addAttribute("categoryName", "Some category");
        return "home";
    }
}
