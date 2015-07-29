package com.feedlyclone.service;

import com.feedlyclone.dto.RssCategoryDTO;
import com.feedlyclone.dto.SyndFeedDTO;
import com.feedlyclone.dto.UserDTO;
import com.feedlyclone.exceptions.FeedServiceException;
import com.feedlyclone.util.CommonFeedUtil;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * provide operations to parsing remote feed and map to object
 */

@Service
public class FeedWorkerService {
    private static final Logger LOGGER = Logger.getLogger(FeedWorkerService.class.getSimpleName());

    public SyndFeedDTO readFeedFromUrl(String url) throws FeedServiceException {
        if (StringUtils.isEmpty(url)) {
            LOGGER.debug("empty url");
            return null;
        }

        URL feedUrl;
        try {
            feedUrl = new URL(url);
        } catch (MalformedURLException e) {
            LOGGER.debug(e);
            throw new FeedServiceException("incorrect url: " + url);
        }

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed;
        try {
            feed = input.build(new XmlReader(feedUrl));
        } catch (FeedException | IOException e) {
            LOGGER.debug(e);
            throw new FeedServiceException("cannot get feed for url={"+url+"}");
        }

        if (feed != null){
            SyndFeedDTO feedHolder = new SyndFeedDTO(CommonFeedUtil.copySyndFeed(feed));
            feedHolder.setLink(feed.getLink());
            feedHolder.setDescription(feed.getDescription());
            feedHolder.setPublishedDate(feed.getPublishedDate());
            feedHolder.setTitle(feed.getTitle());

            return feedHolder;
        } else {
            throw new FeedServiceException("empty feed for url={"+url+"}");
        }

    }

    public SyndFeedDTO readFeedForCategory(RssCategoryDTO category) {
        SyndFeedDTO commonFeedHolder = new SyndFeedDTO();
        if (category != null && !CollectionUtils.isEmpty(category.getFeedUrls())){
            for (String url : category.getFeedUrls()){
                SyndFeedDTO syndFeedDTO = null;
                try {
                    syndFeedDTO = readFeedFromUrl(url);
                } catch (FeedServiceException e) {
                    LOGGER.error(e);
                }
                if (syndFeedDTO != null && !CollectionUtils.isEmpty(syndFeedDTO.getFeedMessages())) {
                    commonFeedHolder.addFeedMessages(syndFeedDTO.getFeedMessages());
                }
            }
        }
        return commonFeedHolder;
    }

    public List<SyndFeedDTO> getAggregateFeedForUser(UserDTO user) {
        List<SyndFeedDTO> result = new ArrayList<>();
        if (user != null && user.getAccount() != null && CollectionUtils.isEmpty(user.getAccount().getRssCategories())){
            user.getAccount().getRssCategories().stream().forEach(rssCategory -> {
                SyndFeedDTO categoryFeed = readFeedForCategory(rssCategory);
                if (categoryFeed != null){
                    result.add(categoryFeed);
                }
            });
        }
        return result;
    }
}
