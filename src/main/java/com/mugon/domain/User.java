package com.mugon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull(message = "id는 필수 항목입니다.")
    @Size(min = 4, message = "id는 최소 4글자 이상입니다.")
    @Column
    private String id;

    @NotNull(message = "password는 필수 항목입니다.")
    @Size(min = 4, message = "password는 최소 4글자 이상입니다.")
    @Column
    private String password;

    @NotNull(message = "email은 필수 항목입니다.")
    @Column
    @Email(message = "email형식을 지켜주세요.")
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
