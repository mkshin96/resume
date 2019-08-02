package com.mugon.controller;

import com.mugon.domain.Projects;
import com.mugon.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    @Autowired
    ProjectsService projectsService;

    @GetMapping
    public String getProjects(Model model){
        model.addAttribute("project", projectsService.findProject());

        return "/projects";
    }

    @GetMapping("/register")
    public String getRegisterProject(){
        return "/registerProject";
    }

    @GetMapping("/modified/{idx}" )
    public String getModifiedProject(@PathVariable Long idx, Model model){
        model.addAttribute("project", projectsService.findModifiedProject(idx));

        return "/modifiedProject";
    }

    @PostMapping
    public ResponseEntity<?> saveProject(@RequestBody Projects projects){
        projectsService.saveProject(projects);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> modifiedProject(@PathVariable Long idx, @RequestBody Projects projects){
        Projects modified = projectsService.findAndModifiedProject(idx, projects);
        projectsService.modifiedProject(modified);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteProject(@PathVariable Long idx){
        projectsService.deleteProject(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
