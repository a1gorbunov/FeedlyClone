package com.feedlyclone.service;

import com.feedlyclone.dto.UserDTO;
import com.feedlyclone.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FeedSecurityService {

    @Autowired
    private UserService userService;

    public String getCurrentUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public UserDTO getCurrentUser() throws NotFoundException {
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null && !StringUtils.isEmpty(user.getUsername())){
            return userService.getUser(user.getUsername());
        } else {
            throw new NotFoundException("no authenticated user found");
        }
    }
}
