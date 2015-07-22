package com.feedlyclone.web;

import com.feedlyclone.domain.entity.FeedMessage;
import com.feedlyclone.service.FeedMessageService;
import com.feedlyclone.service.FeedWorkerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FeedController {
    private static final Logger LOGGER = Logger.getLogger(FeedController.class.getSimpleName());

    @Autowired
    private FeedMessageService feedMessageService;

    @Autowired
    private FeedWorkerService feedWorkerService;

    @RequestMapping(value = "/addFeed")
    public String addFeed(@RequestParam("newFeedValue") String newFeedValue){
        LOGGER.debug("add new feed: " + newFeedValue);

        return "home";
    }

    @ModelAttribute("feedMessages")
    public List<FeedMessage> feedMessages() {
        return feedMessageService.getAll();
    }

}
