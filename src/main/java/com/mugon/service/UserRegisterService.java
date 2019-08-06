package com.mugon.service;

import com.mugon.domain.User;
import com.mugon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public boolean checkId(User user) {
        if(userRepository.findById(user.getId()) == null) return true;
        else return false;
    }

    public boolean checkEmail(User user) {
        if(userRepository.findByEmail(user.getEmail()) == null) return true;
        else return false;
    }
}
