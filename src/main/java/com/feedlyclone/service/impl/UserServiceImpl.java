package com.feedlyclone.service.impl;

import com.feedlyclone.domain.entity.Account;
import com.feedlyclone.domain.entity.User;
import com.feedlyclone.domain.repository.UserRepository;
import com.feedlyclone.dto.UserDTO;
import com.feedlyclone.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO save(UserDTO userDTO) throws DataIntegrityViolationException {
        if (userDTO != null){
            User user = new User();
            BeanUtils.copyProperties(userDTO, user);
            user = userRepository.save(user);
            userDTO.setId(user.getId());
        }
        return userDTO;
    }

    @Override
    public UserDTO getUser(String name) {
        User user = userRepository.findByName(name);
        UserDTO userDTO = null;
        if (user != null){
            userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
        }
        return userDTO;
    }

    @Override
    public void addEmptyUser(String username, String password) throws DataIntegrityViolationException{
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        Account account = new Account();
        account.setUser(user);
        user.setAccount(account);
        userRepository.save(user);
    }
}
