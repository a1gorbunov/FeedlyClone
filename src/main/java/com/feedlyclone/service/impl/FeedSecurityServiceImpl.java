package com.feedlyclone.service.impl;

import com.feedlyclone.domain.entity.User;
import com.feedlyclone.dto.UserDTO;
import com.feedlyclone.service.FeedSecurityService;
import com.feedlyclone.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FeedSecurityServiceImpl implements FeedSecurityService {

    @Autowired
    private UserService userService;

    @Override
    public String getCurrentUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @Override
    public UserDTO getCurrentUser() {
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null && StringUtils.isEmpty(user.getUsername())){
            return userService.getUser(user.getUsername());
        }
        return  null;
    }
}
