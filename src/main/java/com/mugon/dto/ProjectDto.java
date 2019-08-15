package com.mugon.dto;

import com.mugon.domain.Projects;
import com.mugon.domain.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class ProjectDto {

    @NotBlank(message = "필수항목입니다.")
    private String name;

    //기간
    private String period;

    //내용
    private String description;

    //인원 수
    private String persons;

    //등록 일자자
   private LocalDateTime registeredDate;


    public Projects setProject(User currentUser) {
        Projects projects = new Projects();
        projects.setName(this.name);
        projects.setPeriod(this.period);
        projects.setDescription(this.description);
        projects.setPersons(this.persons);
        projects.setRegisteredDate(LocalDateTime.now());
        //유저와 매핑
        projects.setUsers(currentUser);

        return projects;
    }
}
