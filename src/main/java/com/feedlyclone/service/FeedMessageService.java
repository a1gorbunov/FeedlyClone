package com.feedlyclone.service;

import com.feedlyclone.domain.entity.FeedMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * provide access to feedMessage model
 */
@Service
public interface FeedMessageService {

    List<FeedMessage> getAll();
}
