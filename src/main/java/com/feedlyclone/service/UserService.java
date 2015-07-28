package com.feedlyclone.service;

import com.feedlyclone.dto.UserDTO;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *  operations with user
 */

public interface UserService {

    UserDTO save(UserDTO user) throws DataIntegrityViolationException;

    UserDTO getUser(String name);

    void addEmptyUser(String name, String password) throws DataIntegrityViolationException;
}
