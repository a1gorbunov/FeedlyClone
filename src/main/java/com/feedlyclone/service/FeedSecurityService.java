package com.feedlyclone.service;

import com.feedlyclone.domain.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  provide access to security information (current session user and etc.)
 */

public interface FeedSecurityService {

    String getCurrentUserName();

    User getCurrentUser();
}
