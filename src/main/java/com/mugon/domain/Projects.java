package com.mugon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table
@Entity
@NoArgsConstructor
@Setter
@Getter
public class Projects implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String name;

    @Column
    private String period;

    @Column
    private String description;

    @Column
    private String persons;

    @Column
    private LocalDateTime registeredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Builder
    public Projects(String name, String period, String description, String persons, LocalDateTime registeredDate, User user) {
        this.name = name;
        this.period = period;
        this.description = description;
        this.persons = persons;
        this.registeredDate = registeredDate;
        this.user = user;
    }
}
