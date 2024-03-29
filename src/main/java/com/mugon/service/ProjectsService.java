package com.mugon.service;

import com.mugon.domain.Projects;
import com.mugon.domain.User;
import com.mugon.dto.ProjectDto;
import com.mugon.repository.ProjectsRepository;
import com.mugon.sortObject.AscendingProjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProjectsService {

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    AscendingProjects ascendingProjects;

    public void saveProject(ProjectDto projectDto, User currentUser) {
        projectsRepository.save(projectDto.setProject(currentUser));
    }


    public void modifiedProject(Projects projects) {
        projectsRepository.save(projects);
    }


    public List<Projects> findProjectById(User user) {
        List<Projects> projects = projectsRepository.findByUser(user);

        Collections.sort(projects, ascendingProjects);
        return projects;
    }

    //idx순으로 정렬 후 반환
    public List<Projects> findProject() {
        List<Projects> projects = projectsRepository.findAll();

        Collections.sort(projects, ascendingProjects);
        return projects;
    }

    public Projects findModifiedProject(Long idx) {
        return projectsRepository.getOne(idx);
    }

    public Projects findAndModifiedProject(Long idx, ProjectDto modifiedProject) {
        Projects projects = projectsRepository.getOne(idx);

        projects.setName(modifiedProject.getName());
        projects.setPeriod(modifiedProject.getPeriod());
        projects.setDescription(modifiedProject.getDescription());
        projects.setPersons(modifiedProject.getPersons());

        return projects;
    }

    public void deleteProject(Long idx) {
        projectsRepository.deleteById(idx);
    }


}
