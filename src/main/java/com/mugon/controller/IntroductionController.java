package com.mugon.controller;

import com.mugon.domain.Introduction;
import com.mugon.service.IntroductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/introduction")
public class IntroductionController {

    @Autowired
    private IntroductionService introductionService;

    @GetMapping
    public String getIntroduction(Model model){
        model.addAttribute("introduction", introductionService.findIntroduction());
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
    public ResponseEntity<?> saveIntroduction(@RequestBody Introduction introduction){
        introductionService.saveIntroduction(introduction);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> modifiedIntroduction(@PathVariable Long idx, @RequestBody Introduction introduction){
        introductionService.findAndModifiedIntroduction(idx, introduction);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
    
    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteIntroduction(@PathVariable Long idx){
        introductionService.deleteIntroduction(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
