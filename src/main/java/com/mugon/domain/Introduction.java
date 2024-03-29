package com.mugon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@Setter
@Getter
public class Introduction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String title;

    //성장배경
    @Column
    private String growth;

    //지원동기
    @Column
    private String reason;

    //장점
    @Column
    private String strength;

    //단점
    @Column
    private String weakness;

    //입사 후 포부
    @Column
    private String aspiration;

    @Column
    private LocalDateTime registeredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Builder
    public Introduction(String title, String growth, String reason, String strength, String weakness, String aspiration, LocalDateTime registeredDate, User user) {
        this.title = title;
        this.growth = growth;
        this.reason = reason;
        this.strength = strength;
        this.weakness = weakness;
        this.aspiration = aspiration;
        this.registeredDate = registeredDate;
        this.user = user;
    }

    public void setUsers(User currentUser) {

        if(this.user != null) this.user.getIntroductionSet().remove(this);

        this.user = currentUser;
        currentUser.getIntroductionSet().add(this);
    }
}
