package com.feedlyclone.web;

import com.feedlyclone.dto.FeedMessageView;
import com.feedlyclone.service.FeedMessageService;
import com.feedlyclone.service.FeedWorkerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class FeedController {
    private static final Logger LOGGER = Logger.getLogger(FeedController.class.getSimpleName());

    @Autowired
    private FeedMessageService feedMessageService;

    private List<FeedMessageView> feedMessages;

    @Autowired
    private FeedWorkerService feedWorkerService;

    @RequestMapping(value = "/addFeed")
    public String addFeed(@RequestParam("newFeedValue") String newFeedUrl, Model model){
        LOGGER.debug("add new feed: " + newFeedUrl);
        feedMessages = feedWorkerService.readFeedFromUrl(newFeedUrl);
        model.addAttribute("feedMessages", feedMessages);
        model.addAttribute("categoryName", "Some category");
        return "home";
    }

    @RequestMapping(value = "/fullMode")
    public ModelAndView openFullMode(@RequestParam Integer id){
        LOGGER.debug("open full mode for feed message with id="+id);
        if (feedMessages != null && feedMessages.get(id) != null){
            ModelAndView mav = new ModelAndView("article");
            mav.addObject("descriptionFull", feedMessages.get(id).getDescriptionFull());
            return mav;
        }

        return new ModelAndView("home");
    }
}
