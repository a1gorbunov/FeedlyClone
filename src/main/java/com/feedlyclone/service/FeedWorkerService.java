package com.feedlyclone.service;

import com.feedlyclone.domain.entity.RssCategory;
import com.feedlyclone.domain.entity.User;
import com.feedlyclone.dto.RssCategoryDTO;
import com.feedlyclone.dto.SyndFeedDTO;
import com.feedlyclone.dto.UserDTO;

import java.util.List;

/**
 * service for process syndicate feeds
 */
public interface FeedWorkerService {

    /**
     * get feed band by specific url
     * @return holder which contains all required information
     */
    SyndFeedDTO readFeedFromUrl(String url);

    List<SyndFeedDTO> getAggregateFeedForUser(UserDTO user);

    SyndFeedDTO readFeedForCategory(RssCategoryDTO category);
}
