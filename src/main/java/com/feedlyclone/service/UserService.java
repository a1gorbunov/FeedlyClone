package com.feedlyclone.service;

import com.feedlyclone.domain.entity.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *  operations with user
 */

public interface UserService {

    void save(User user) throws DataIntegrityViolationException;

    User getUser(String name);

    void addEmptyUser(String name, String password) throws DataIntegrityViolationException;
}
