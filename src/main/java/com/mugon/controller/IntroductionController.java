package com.mugon.controller;

import com.mugon.domain.User;
import com.mugon.dto.IntroductionDto;
import com.mugon.service.IntroductionService;
import com.mugon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/introduction")
public class IntroductionController {

    @Autowired
    private IntroductionService introductionService;

    @Autowired
    private UserService userService;

    private User currentUser;

    @GetMapping
    public String getIntroduction(Model model){
        org.springframework.security.core.userdetails.User user= (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        currentUser = userService.findCurrentUser(user);
        model.addAttribute("introduction", introductionService.findIntroduction(currentUser));
        return "/introduction/introduction";
    }

    @GetMapping("/register")
    public String getRegisterIntroduction(){
        return "/introduction/registerIntroduction";
    }

    @GetMapping("/modified/{idx}" )
    public String getModifiedProject(@PathVariable Long idx, Model model){
        model.addAttribute("introduction", introductionService.findModiIntroduction(idx));

        return "/introduction/modifiedIntroduction";
    }

    @PostMapping
    public ResponseEntity<?> saveIntroduction(@Valid @RequestBody IntroductionDto introductionDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
        else {
            introductionService.saveIntroduction(introductionDto, currentUser);
            return new ResponseEntity<>("{}", HttpStatus.CREATED);
        }
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> modifiedIntroduction(@PathVariable Long idx, @Valid @RequestBody IntroductionDto introductionDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        else{
            introductionService.findAndModifiedIntroduction(idx, introductionDto);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        }
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteIntroduction(@PathVariable Long idx){
        introductionService.deleteIntroduction(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
