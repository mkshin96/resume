package com.mugon.domain;

import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String id;

    @Column
    private String password;

    @Column
    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Projects> projectsList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Introduction> introductionList;

    @Builder
    public User(String id, String password, String email, List<Projects> projectsList, List<Introduction> introductionList) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.projectsList = projectsList;
        this.introductionList = introductionList;
    }

    public void addProject(Projects projects) {
        projectsList.add(projects);
    }

    public void addIntroduction(Introduction introduction) {
        introductionList.add(introduction);
    }
}
