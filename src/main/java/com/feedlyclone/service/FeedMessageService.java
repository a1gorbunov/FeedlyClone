package com.feedlyclone.service;

import com.feedlyclone.domain.entity.FeedMessage;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * provide access to feedMessage model
 */
@Service
public interface FeedMessageService {

    List<FeedMessage> getAll();

    List<FeedMessage> saveBatch(Collection<FeedMessage> feedMessages);
}
