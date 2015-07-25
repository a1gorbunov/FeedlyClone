package com.feedlyclone.service;

import com.feedlyclone.domain.entity.User;
import org.springframework.stereotype.Service;

/**
 *  operations with user
 */

@Service
public interface UserService {

    void save(User user);

    User getUser(String name);

    void addEmptyUser(String name, String password);
}
