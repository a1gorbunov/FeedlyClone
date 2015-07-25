package com.feedlyclone.service.impl;

import com.feedlyclone.domain.UserRepository;
import com.feedlyclone.domain.entity.Account;
import com.feedlyclone.domain.entity.User;
import com.feedlyclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user){
        userRepository.save(user);
    }

    @Override
    public User getUser(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public void addEmptyUser(String username, String password) {
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        Account account = new Account();
        account.setUser(user);
        user.setAccount(account);
        save(user);
    }
}