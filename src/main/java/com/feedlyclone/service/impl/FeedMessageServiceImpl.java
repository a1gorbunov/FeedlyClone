package com.feedlyclone.service.impl;

import com.feedlyclone.domain.FeedMessageRepository;
import com.feedlyclone.domain.entity.FeedMessage;
import com.feedlyclone.service.FeedMessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeedMessageServiceImpl implements FeedMessageService {
    private static final Logger LOGGER = Logger.getLogger(FeedMessageServiceImpl.class.getSimpleName());

    @Autowired
    private FeedMessageRepository feedMessageRepository;

    @Override
    @Transactional
    public List<FeedMessage> getAll(){
        LOGGER.debug("trying to get all feed messages");
        return feedMessageRepository.findAll();
    }
}