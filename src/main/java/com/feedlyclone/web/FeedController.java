package com.feedlyclone.web;

import com.feedlyclone.dto.AccountDTO;
import com.feedlyclone.dto.RssCategoryDTO;
import com.feedlyclone.dto.SyndFeedDTO;
import com.feedlyclone.dto.UserDTO;
import com.feedlyclone.service.AccountService;
import com.feedlyclone.service.FeedSecurityService;
import com.feedlyclone.service.FeedWorkerService;
import com.feedlyclone.service.RssCategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class FeedController {
    private static final Logger LOGGER = Logger.getLogger(FeedController.class.getSimpleName());

    private SyndFeedDTO feedHolder;

    @Autowired
    private FeedWorkerService feedWorkerService;

    @Autowired
    private RssCategoryService categoryService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private FeedSecurityService feedSecurityService;

    /**
     * add new feed to user account and display chosen news band
     */
    @RequestMapping(value = "/addFeed")
    public String addFeed(@RequestParam("newFeedValue") String newFeedUrl, Model model){
        LOGGER.debug("add new feed: " + newFeedUrl);
        feedHolder = feedWorkerService.readFeedFromUrl(newFeedUrl);
        if(feedHolder != null ) {
            UserDTO user = feedSecurityService.getCurrentUser();
            String feedTitle = feedHolder.getTitle();
            if (user != null && user.getAccount() != null && !StringUtils.isEmpty(feedTitle)) {
                AccountDTO account = user.getAccount();
                accountService.addFeedToAccount(account.getId(), feedTitle, newFeedUrl);

                model.addAttribute("feedMessages", feedHolder.getFeedMessages());
                model.addAttribute("categoryName", feedTitle);
            }
        }
        return "home";
    }

    /**
     * return full description for user chosen news
     */
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

    /**
     * open home and load all required objects to model
     */
    @RequestMapping(value = "/home")
    public String home(ModelMap modelMap){
        UserDTO user = feedSecurityService.getCurrentUser();
        if (user != null && StringUtils.isEmpty(user.getName())) {
            modelMap.addAttribute("username", user.getName());
            List<RssCategoryDTO> categories = user.getAccount().getRssCategories();
            if (!CollectionUtils.isEmpty(categories)) {
                modelMap.addAttribute("categories", user.getAccount().getRssCategories());
            }

            List<SyndFeedDTO> allFeeds = feedWorkerService.getAggregateFeedForUser(user);
            if(allFeeds != null ) {
                //modelMap.addAttribute("feedMessages", feedHolder.getFeedMessages());
                modelMap.addAttribute("categoryName", "All your feeds");
            }
        }
        return "home";
    }
}
