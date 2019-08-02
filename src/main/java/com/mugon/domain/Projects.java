package com.mugon.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity
@NoArgsConstructor
@Setter
@ToString
@Getter
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
    private String persons;

    @Column
    private LocalDateTime registeredDate;

    @Builder
    public Projects(String name, String period, String description, String persons, LocalDateTime registeredDate) {
        this.name = name;
        this.period = period;
        this.description = description;
        this.persons = persons;
        this.registeredDate = registeredDate;
    }
}
