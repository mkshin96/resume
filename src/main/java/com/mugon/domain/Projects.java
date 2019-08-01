package com.mugon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity
@NoArgsConstructor
public class Projects {

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
    private int persons;

    @Column
    private LocalDateTime registeredDate;

    @Builder
    public Projects(String name, String period, String description, int persons, LocalDateTime registeredDate) {
        this.name = name;
        this.period = period;
        this.description = description;
        this.persons = persons;
        this.registeredDate = registeredDate;
    }
}
