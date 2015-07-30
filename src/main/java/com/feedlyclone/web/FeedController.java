package com.feedlyclone.web;

import com.feedlyclone.dto.AccountDTO;
import com.feedlyclone.dto.RssCategoryDTO;
import com.feedlyclone.dto.SyndFeedDTO;
import com.feedlyclone.dto.UserDTO;
import com.feedlyclone.exceptions.FeedServiceException;
import com.feedlyclone.exceptions.NotFoundException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FeedController {
    private static final Logger LOGGER = Logger.getLogger(FeedController.class.getSimpleName());

    @Autowired
    private FeedWorkerService feedWorkerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RssCategoryService categoryService;

    @Autowired
    private FeedSecurityService feedSecurityService;

    private List<SyndFeedDTO> feedHolder = new ArrayList<>();

    /**
     * add new feed to user account and display chosen news band
     */
    @RequestMapping(value = "/addFeed")
    public String addFeed(@RequestParam("newFeedValue") String newFeedUrl, Model model){
        LOGGER.debug("add new feed: " + newFeedUrl);
        UserDTO user;
        SyndFeedDTO feedNew = null;
        try {
            user = feedSecurityService.getCurrentUser();
        } catch (NotFoundException e) {
            model.addAttribute("invalidate", e.getMessage());
            return "login";
        }
        try {
            feedNew = feedWorkerService.readFeedFromUrl(newFeedUrl);
        } catch (FeedServiceException e) {
            model.addAttribute("invalidate", "unexpected error during process url " + newFeedUrl);
            return "error";
        }
        if(feedNew != null ) {
            String feedTitle = feedNew.getTitle();
            if (user != null && user.getAccount() != null && !StringUtils.isEmpty(feedTitle)) {
                AccountDTO account = user.getAccount();
                try {
                    accountService.addFeedToAccount(account.getId(), feedTitle, newFeedUrl);
                } catch (NotFoundException e) {
                    model.addAttribute("invalidate", "current account not found");
                    return "login";
                }
            }
        }
        return "redirect:home";
    }

    /**
     * return full description for user chosen news
     */
    @RequestMapping(value = "/descriptionFull")
    public
    @ResponseBody
    String getDescription(@RequestParam String id, @RequestParam String title) {
        LOGGER.debug("get description for id= " + id);
        if (feedHolder != null && !StringUtils.isEmpty(id) && !StringUtils.isEmpty(title)) {
            List<SyndFeedDTO> feedMessage =
                    feedHolder.stream().filter(syndFeedDTO -> title.equals(syndFeedDTO.getTitle())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(feedMessage)){
                return feedMessage.get(0).getFeedMessages().get(Integer.valueOf(id)).getDescriptionFull();
            }
        }
        return "";
    }

    /**
     * open home and load all required objects to model
     */
    @RequestMapping(value = "/home")
    public String home(ModelMap modelMap){
        UserDTO user;
        try {
            user = feedSecurityService.getCurrentUser();
        } catch (NotFoundException e) {
            LOGGER.debug(e);
            return "login";
        }
        if (user != null && user.getAccount() != null) {
            List<RssCategoryDTO> categories = categoryService.getCategoriesForAccount(user.getAccount());
            if (!CollectionUtils.isEmpty(categories)) {
                modelMap.addAttribute("categories", categories);
            }
            feedHolder = feedWorkerService.getAggregateFeedForUser(user);
            if(!CollectionUtils.isEmpty(feedHolder)) {
                modelMap.addAttribute("feedHolder", feedHolder);
            }
            modelMap.addAttribute("username", user.getName());
            modelMap.addAttribute("categoryName", "All your feeds");
        }
        return "home";
    }

    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public String readFeed(@RequestParam String url, Model model){
        SyndFeedDTO result;
        try {
            result = feedWorkerService.readFeedFromUrl(url);
        } catch (FeedServiceException e) {
            model.addAttribute("invalidate", "unexpected error during process url " + url);
            return "error";
        }
        if (result != null){
            feedHolder = new ArrayList<>(Arrays.asList(result));
            model.addAttribute("feedHolder", feedHolder);
        }
        return "home";
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<RssCategoryDTO> getCategories(){
        UserDTO user = null;
        try {
            user = feedSecurityService.getCurrentUser();
        } catch (NotFoundException e) {
            LOGGER.debug(e);
        }
        if (user != null && user.getAccount() != null) {
            List<RssCategoryDTO> categories = categoryService.getCategoriesForAccount(user.getAccount());
            if (!CollectionUtils.isEmpty(categories)) {
                return categories;
            }
        }
        return new ArrayList<>();
    }
}
