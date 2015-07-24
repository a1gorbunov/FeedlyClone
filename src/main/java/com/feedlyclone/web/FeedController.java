package com.feedlyclone.web;

import com.feedlyclone.service.FeedMessageService;
import com.feedlyclone.service.FeedWorkerService;
import com.feedlyclone.util.SyndFeedHolder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FeedController {
    private static final Logger LOGGER = Logger.getLogger(FeedController.class.getSimpleName());

    private SyndFeedHolder feedHolder;

    @Autowired
    private FeedWorkerService feedWorkerService;

    @RequestMapping(value = "/addFeed")
    public String addFeed(@RequestParam("newFeedValue") String newFeedUrl, Model model){
        LOGGER.debug("add new feed: " + newFeedUrl);
        feedHolder = feedWorkerService.readFeedFromUrl(newFeedUrl);
        if(feedHolder != null ) {
            model.addAttribute("feedMessages", feedHolder.getFeedMessages());
            model.addAttribute("categoryName", feedHolder.getTitle());
        }
        return "home";
    }

    @RequestMapping(value = "/description/{id}")
    public
    @ResponseBody
    String getDescription(@PathVariable String id) {
        LOGGER.debug("get description for id= " + id);
        if (feedHolder != null && !StringUtils.isEmpty(id)) {
            return feedHolder.getFeedMessages().get(Integer.valueOf(id)).getDescriptionFull();
        }
        return "";
    }
}
