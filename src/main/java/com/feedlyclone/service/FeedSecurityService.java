package com.feedlyclone.service;

import com.feedlyclone.dto.UserDTO;

/**
 *  provide access to security information (current session user and etc.)
 */

public interface FeedSecurityService {

    String getCurrentUserName();

    UserDTO getCurrentUser();
}
