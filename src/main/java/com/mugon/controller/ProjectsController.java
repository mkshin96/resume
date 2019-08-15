package com.mugon.controller;

import com.mugon.domain.Projects;
import com.mugon.dto.ProjectDto;
import com.mugon.service.ProjectsService;
import com.mugon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    @Autowired
    ProjectsService projectsService;

    @Autowired
    UserService userService;

    private com.mugon.domain.User currentUser;

    @GetMapping
    public String getProjects(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        currentUser = userService.findCurrentUser(user);

        model.addAttribute("project", projectsService.findProjectById(currentUser));

        return "/project/projects";
    }

    @GetMapping("/register")
    public String getRegisterProject(){
        return "/project/registerProject";
    }

    @GetMapping("/modified/{idx}" )
    public String getModifiedProject(@PathVariable Long idx, Model model){
        model.addAttribute("project", projectsService.findModifiedProject(idx));

        return "/project/modifiedProject";
    }

    @PostMapping
    public ResponseEntity<?> saveProject(@Valid @RequestBody ProjectDto projectDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        else {
            projectsService.saveProject(projectDto, currentUser);
            return new ResponseEntity<>("{}", HttpStatus.CREATED);
        }
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> modifiedProject(@PathVariable Long idx, @Valid @RequestBody ProjectDto projectDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        else{
            Projects modified = projectsService.findAndModifiedProject(idx, projectDto);
            projectsService.modifiedProject(modified);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        }
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteProject(@PathVariable Long idx){
        projectsService.deleteProject(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
