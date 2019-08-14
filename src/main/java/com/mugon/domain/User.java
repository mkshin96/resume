package com.mugon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@Setter
@Getter
public class User implements Serializable {

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
