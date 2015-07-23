package com.feedlyclone.service.impl;

import com.feedlyclone.dto.FeedMessageView;
import com.feedlyclone.service.FeedMessageService;
import com.feedlyclone.service.FeedWorkerService;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FeedMessageService feedMessageService;

    @Override
    public List<FeedMessageView> readFeedFromUrl(String url) {
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
            return copySyndFeed(feed);
        }

        return null;
    }

    /**
     * process syndFeed to List of FeedMessageView objects
     * @param syndFeed feed data by ROME
     * @return List of FeedMessageView
     */
    private List<FeedMessageView> copySyndFeed(SyndFeed syndFeed) {
        List<FeedMessageView> result = new ArrayList<>();
        if (syndFeed != null && syndFeed.getEntries() != null && syndFeed.getEntries().size() > 0) {
            for (int i = 0; i < syndFeed.getEntries().size();i++){
                SyndEntry syndEntry = syndFeed.getEntries().get(i);
                FeedMessageView feedMessageView = new FeedMessageView();
                feedMessageView.setId(i);
                feedMessageView.setAuthor(syndEntry.getAuthor());
                feedMessageView.setDescriptionClean(removeHtmlTag(syndEntry.getDescription().getValue()));
                feedMessageView.setDescriptionFull(syndEntry.getDescription().getValue());
                feedMessageView.setLink(syndEntry.getLink());
                feedMessageView.setTitle(syndEntry.getTitle());
                feedMessageView.setPublishDate(syndEntry.getPublishedDate());
                if (!CollectionUtils.isEmpty(syndEntry.getEnclosures())) {
                    feedMessageView.setImage(
                            syndEntry.getEnclosures().stream().filter(syndEnclosure -> syndEnclosure.getType().contains("image"))
                                    .findFirst().get().getUrl());
                }
                result.add(feedMessageView);
            }
        }

        return result;
    }

    private String removeHtmlTag(String content){
        return Jsoup.parseBodyFragment(content).body().text();
    }
}
