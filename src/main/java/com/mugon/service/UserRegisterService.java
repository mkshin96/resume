package com.mugon.service;

import com.mugon.domain.User;
import com.mugon.dto.UserDto;
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

    public void saveUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(userDto.setUser(userDto));
    }

    public boolean checkId(User user) {
        return userRepository.findById(user.getId()) == null;
    }

    public boolean checkEmail(User user) {
        return userRepository.findByEmail(user.getEmail()) == null;
    }
}
