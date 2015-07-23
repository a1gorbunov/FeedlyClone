package com.feedlyclone.service;

import com.feedlyclone.dto.FeedMessageView;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service for process syndicate feeds
 */
@Service
public interface FeedWorkerService {

    List<FeedMessageView> readFeedFromUrl(String url);
}
