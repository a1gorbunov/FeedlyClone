package com.feedlyclone.service.impl;

import com.feedlyclone.service.FeedWorkerService;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class FeedWorkerServiceImpl implements FeedWorkerService {

    private static final Logger LOGGER = Logger.getLogger(FeedWorkerServiceImpl.class.getSimpleName());

    @Override
    @Transactional
    public SyndFeed readFeedFromUrl(String url) {
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
        SyndFeed feed = null;
        try {
            feed = input.build(new XmlReader(feedUrl));
        } catch (FeedException | IOException e) {
            LOGGER.debug(e);
            return null;
        }

        return feed;
    }
}
