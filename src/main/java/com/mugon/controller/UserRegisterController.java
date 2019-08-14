package com.mugon.controller;

import com.mugon.domain.User;
import com.mugon.dto.UserDto;
import com.mugon.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
    public ResponseEntity<?> postUser(@Valid @RequestBody UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        } else {
            userRegisterService.saveUser(userDto);
            return new ResponseEntity<>("{}", HttpStatus.CREATED);
        }
    }
    //id 중복검사
    @PostMapping("/checkId")
    public ResponseEntity<?> checkId(@RequestBody User user){
        if(userRegisterService.checkId(user)) return new ResponseEntity<>("{}", HttpStatus.OK);
        else return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestBody User user){
        if(userRegisterService.checkEmail(user)) return new ResponseEntity<>("{}", HttpStatus.OK);
        else return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
    }

}
