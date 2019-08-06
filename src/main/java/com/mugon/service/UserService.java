package com.mugon.service;

import com.mugon.domain.User;
import com.mugon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findCurrentUser(org.springframework.security.core.userdetails.User user) {
        return userRepository.findById(user.getUsername());
    }
}
