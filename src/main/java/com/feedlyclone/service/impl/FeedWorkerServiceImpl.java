package com.feedlyclone.service.impl;

import com.feedlyclone.domain.entity.RssCategory;
import com.feedlyclone.domain.entity.User;
import com.feedlyclone.dto.FeedMessage;
import com.feedlyclone.service.FeedWorkerService;
import com.feedlyclone.util.SyndFeedHolder;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
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
    public SyndFeedHolder readFeedFromUrl(String url) {
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
            SyndFeedHolder feedHolder = new SyndFeedHolder(copySyndFeed(feed));
            feedHolder.setLink(feed.getLink());
            feedHolder.setDescription(feed.getDescription());
            feedHolder.setPublishedDate(feed.getPublishedDate());
            feedHolder.setTitle(feed.getTitle());

            return feedHolder;
        }

        return null;
    }

    /**
     * process syndFeed to List of FeedMessage objects
     * @param syndFeed feed data by ROME
     * @return List of FeedMessage
     */
    private List<FeedMessage> copySyndFeed(SyndFeed syndFeed) {
        List<FeedMessage> result = new ArrayList<>();
        if (syndFeed != null && syndFeed.getEntries() != null && syndFeed.getEntries().size() > 0) {
            for (int i = 0; i < syndFeed.getEntries().size();i++){
                SyndEntry syndEntry = syndFeed.getEntries().get(i);
                FeedMessage feedMessage = new FeedMessage();
                feedMessage.setId(i);
                feedMessage.setAuthor(syndEntry.getAuthor());
                feedMessage.setDescriptionClean(removeHtmlTag(syndEntry.getDescription().getValue()));
                feedMessage.setDescriptionFull(syndEntry.getDescription().getValue());
                feedMessage.setLink(syndEntry.getLink());
                feedMessage.setTitle(syndEntry.getTitle());
                feedMessage.setPublishDate(syndEntry.getPublishedDate());
                if (!CollectionUtils.isEmpty(syndEntry.getEnclosures())) {
                    feedMessage.setImage(
                            syndEntry.getEnclosures().stream().filter(syndEnclosure -> syndEnclosure.getType().contains("image"))
                                    .findFirst().get().getUrl());
                }
                result.add(feedMessage);
            }
        }

        return result;
    }

    @Override
    public SyndFeedHolder readFeedForCategory(RssCategory category) {
        if (category != null && !CollectionUtils.isEmpty(category.getFeedUrls())){
            SyndFeedHolder commonFeedHolder = new SyndFeedHolder();
            category.getFeedUrls().stream().forEach(url -> {
                SyndFeedHolder syndFeedHolder = readFeedFromUrl(url);
                if (syndFeedHolder != null && !CollectionUtils.isEmpty(syndFeedHolder.getFeedMessages())) {
                    commonFeedHolder.addFeedMessages(syndFeedHolder.getFeedMessages());
                }
            });
            return commonFeedHolder;
        }
        return null;
    }

     @Override
    public List<SyndFeedHolder> getAggregateFeedForUser(User user) {
        if (user != null && user.getAccount() != null && CollectionUtils.isEmpty(user.getAccount().getRssCategory())){
            List<SyndFeedHolder> result = new ArrayList<>();
            user.getAccount().getRssCategory().stream().forEach(rssCategory -> {
                SyndFeedHolder categoryFeed = readFeedForCategory(rssCategory);
                if (categoryFeed != null){
                    result.add(categoryFeed);
                }
            });
            return result;
        }
        return null;
    }

    private String removeHtmlTag(String content){
        return Jsoup.parseBodyFragment(content).body().text();
    }
}
