package com.mugon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    @GetMapping
    public String getProjects(){
        return "/projects";
    }

    @GetMapping("/register")
    public String getRegisterProject(){
        return "/registerProject";
    }
}
