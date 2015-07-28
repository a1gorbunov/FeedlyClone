package com.feedlyclone.service;

import com.feedlyclone.BaseSpringTest;
import com.feedlyclone.domain.entity.User;
import com.feedlyclone.domain.repository.AccountRepository;
import com.feedlyclone.domain.repository.UserRepository;
import com.feedlyclone.dto.UserDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserServiceTest extends BaseSpringTest{

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void addEmptyUser(){
        String userName = "testuser";
        String userPass = "testpass";

        long beforeCount = userRepository.count();
        userService.addEmptyUser(userName, userPass);
        assertEquals(beforeCount + 1, userRepository.count());
        User user = userRepository.findByName("testuser");
        assertNotNull(user);
        assertNotNull(user.getAccount());
        assertNotNull(user.getAccount().getId());
        assertNotNull(accountRepository.findOne(user.getAccount().getId()));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveDuplicate(){
        String userName = "testuser";

        UserDTO user = new UserDTO();
        user.setName(userName);
        userService.save(user);

        user = new UserDTO();
        user.setName(userName);
        userService.save(user);
    }
}
