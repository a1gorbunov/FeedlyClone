package com.feedlyclone.service;

import com.feedlyclone.util.SyndFeedHolder;
import org.springframework.stereotype.Service;

/**
 * service for process syndicate feeds
 */
@Service
public interface FeedWorkerService {

    /**
     * get feed band by specific url
     * @return holder which contains all required information
     */
    SyndFeedHolder readFeedFromUrl(String url);
}
