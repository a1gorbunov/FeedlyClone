package com.feedlyclone.service.impl;

import com.feedlyclone.domain.entity.RssCategory;
import com.feedlyclone.domain.entity.User;
import com.feedlyclone.dto.RssCategoryDTO;
import com.feedlyclone.dto.SyndFeedDTO;
import com.feedlyclone.dto.UserDTO;
import com.feedlyclone.service.FeedWorkerService;
import com.feedlyclone.util.CommonFeedUtil;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedWorkerServiceImpl implements FeedWorkerService {
    private static final Logger LOGGER = Logger.getLogger(FeedWorkerServiceImpl.class.getSimpleName());

    @Override
    public SyndFeedDTO readFeedFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            LOGGER.debug("empty url");
            return null;
        }

        URL feedUrl;
        try {
            feedUrl = new URL(url);
        } catch (MalformedURLException e) {
            LOGGER.debug("incorrect url: " + url);
            return null;
        }

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed;
        try {
            feed = input.build(new XmlReader(feedUrl));
        } catch (FeedException | IOException e) {
            LOGGER.debug(e);
            return null;
        }

        if (feed != null){
            SyndFeedDTO feedHolder = new SyndFeedDTO(CommonFeedUtil.copySyndFeed(feed));
            feedHolder.setLink(feed.getLink());
            feedHolder.setDescription(feed.getDescription());
            feedHolder.setPublishedDate(feed.getPublishedDate());
            feedHolder.setTitle(feed.getTitle());

            return feedHolder;
        }

        return null;
    }

    @Override
    public SyndFeedDTO readFeedForCategory(RssCategoryDTO category) {
        if (category != null && !CollectionUtils.isEmpty(category.getFeedUrls())){
            SyndFeedDTO commonFeedHolder = new SyndFeedDTO();
            category.getFeedUrls().stream().forEach(url -> {
                SyndFeedDTO syndFeedDTO = readFeedFromUrl(url);
                if (syndFeedDTO != null && !CollectionUtils.isEmpty(syndFeedDTO.getFeedMessages())) {
                    commonFeedHolder.addFeedMessages(syndFeedDTO.getFeedMessages());
                }
            });
            return commonFeedHolder;
        }
        return null;
    }

     @Override
    public List<SyndFeedDTO> getAggregateFeedForUser(UserDTO user) {
        if (user != null && user.getAccount() != null && CollectionUtils.isEmpty(user.getAccount().getRssCategories())){
            List<SyndFeedDTO> result = new ArrayList<>();
            user.getAccount().getRssCategories().stream().forEach(rssCategory -> {
                SyndFeedDTO categoryFeed = readFeedForCategory(rssCategory);
                if (categoryFeed != null){
                    result.add(categoryFeed);
                }
            });
            return result;
        }
        return null;
    }
}
