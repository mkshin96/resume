package com.mugon.domain;

import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @NotNull(message = "id는 필수 항목입니다.")
    @Column
    private String id;

    @NotNull(message = "password는 필수 항목입니다.")
    @Column
    private String password;

    @NotNull(message = "email은 필수 항목입니다.")
    @Column
    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Projects> projectsSet;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Introduction> introductionSet;

    @Builder
    public User(String id, String password, String email, Set<Projects> projectsSet, Set<Introduction> introductionSet) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.projectsSet = projectsSet;
        this.introductionSet = introductionSet;
    }

    public void addProject(Projects projects) {
        projectsSet.add(projects);
    }

    public void addIntroduction(Introduction introduction) {
        introductionSet.add(introduction);
    }
}
