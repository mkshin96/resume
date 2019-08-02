package com.mugon.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
public class Introduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    
}
