package com.mugon.controller;

import com.mugon.domain.User;
import com.mugon.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userRegister")
public class UserRegisterController {

    @Autowired
    UserRegisterService userRegisterService;

    @GetMapping
    public String getUserRegister(){
        return "/relatedLogin/registerUser";
    }

    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody User user){
        userRegisterService.saveUser(user);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
