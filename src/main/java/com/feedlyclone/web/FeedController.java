package com.feedlyclone.web;

import com.feedlyclone.domain.entity.RssCategory;
import com.feedlyclone.domain.entity.User;
import com.feedlyclone.service.FeedSecurityService;
import com.feedlyclone.service.FeedWorkerService;
import com.feedlyclone.service.RssCategoryService;
import com.feedlyclone.util.SyndFeedHolder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class FeedController {
    private static final Logger LOGGER = Logger.getLogger(FeedController.class.getSimpleName());

    private SyndFeedHolder feedHolder;

    @Autowired
    private FeedWorkerService feedWorkerService;

    @Autowired
    private RssCategoryService categoryService;

    @Autowired
    private FeedSecurityService feedSecurityService;

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

    @RequestMapping(value = "/home")
    public String home(ModelMap modelMap){
        User user = feedSecurityService.getCurrentUser();
        if (user != null && StringUtils.isEmpty(user.getName())) {
            modelMap.addAttribute("username", user.getName());
            modelMap.addAttribute("categories", user.getAccount().getRssCategory());
            feedHolder = feedWorkerService.readFeedFromUrl(newFeedUrl);
            if(feedHolder != null ) {
                modelMap.addAttribute("feedMessages", feedHolder.getFeedMessages());
                modelMap.addAttribute("categoryName", feedHolder.getTitle());
            }
        }
        return "home";
    }
}
