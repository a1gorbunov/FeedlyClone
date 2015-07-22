package com.feedlyclone.service.impl;

import com.feedlyclone.domain.entity.FeedMessage;
import com.feedlyclone.service.FeedMessageService;
import com.feedlyclone.service.FeedWorkerService;
import com.feedlyclone.util.RomeFeedWorkerUtil;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class FeedWorkerServiceImpl implements FeedWorkerService {
    private static final Logger LOGGER = Logger.getLogger(FeedWorkerServiceImpl.class.getSimpleName());

    @Autowired
    private FeedMessageService feedMessageService;

    @Override
    public List<FeedMessage> readFeedFromUrl(String url) {
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
            return RomeFeedWorkerUtil.copySyndFeed(feed);
        }

        return null;
    }
}
