package com.feedlyclone.service;

import com.feedlyclone.domain.entity.RssCategory;
import com.feedlyclone.domain.entity.User;
import com.feedlyclone.util.SyndFeedHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service for process syndicate feeds
 */
public interface FeedWorkerService {

    /**
     * get feed band by specific url
     * @return holder which contains all required information
     */
    SyndFeedHolder readFeedFromUrl(String url);

    List<SyndFeedHolder> getAggregateFeedForUser(User user);

    SyndFeedHolder readFeedForCategory(RssCategory category);
}
