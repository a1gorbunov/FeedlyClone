package com.feedlyclone.service;

import com.rometools.rome.feed.synd.SyndFeed;
import org.springframework.stereotype.Service;

/**
 * service for process syndicate feeds
 */
@Service
public interface FeedWorkerService {

    SyndFeed readFeedFromUrl(String url);
}
