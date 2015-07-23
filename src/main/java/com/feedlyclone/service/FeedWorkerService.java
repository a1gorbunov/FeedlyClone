package com.feedlyclone.service;

import com.feedlyclone.dto.FeedMessageView;
import com.feedlyclone.util.SyndFeedHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service for process syndicate feeds
 */
@Service
public interface FeedWorkerService {

    SyndFeedHolder readFeedFromUrl(String url);
}
