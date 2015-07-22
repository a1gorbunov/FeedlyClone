package com.feedlyclone.service;

import com.feedlyclone.domain.entity.FeedMessage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service for process syndicate feeds
 */
@Service
public interface FeedWorkerService {

    List<FeedMessage> readFeedFromUrl(String url);
}
