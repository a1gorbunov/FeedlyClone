package com.feedlyclone.service;

import com.feedlyclone.domain.entity.Account;
import com.feedlyclone.domain.entity.User;
import com.feedlyclone.domain.repository.UserRepository;
import com.feedlyclone.dto.UserDTO;
import com.feedlyclone.exceptions.NotFoundException;
import com.feedlyclone.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDTO save(UserDTO userDTO) throws DataIntegrityViolationException {
        if (userDTO != null){
            User user = userMapper.mapReverse(userDTO);
            user = userRepository.save(user);
            userDTO.setId(user.getId());
        }
        return userDTO;
    }

    public UserDTO getUser(String name) throws NotFoundException {
        User user = userRepository.findByName(name);
        if (user == null){
            throw new NotFoundException("user={"+name+"} not found");
        }
        return userMapper.map(user);
    }

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
