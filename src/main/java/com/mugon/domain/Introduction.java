package com.mugon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@Setter
@Getter
public class Introduction {

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

    @Column
    private Long growthLength;

    @Column
    private Long reasonLength;

    @Column
    private Long strengthLength;

    @Column
    private Long weaknessLength;

    @Column
    private Long aspirationLength;

    @Builder

    public Introduction(String title, String growth, String reason, String strength, String weakness, String aspiration, LocalDateTime registeredDate, Long growthLength, Long reasonLength, Long strengthLength, Long weaknessLength, Long aspirationLength) {
        this.title = title;
        this.growth = growth;
        this.reason = reason;
        this.strength = strength;
        this.weakness = weakness;
        this.aspiration = aspiration;
        this.registeredDate = registeredDate;
        this.growthLength = growthLength;
        this.reasonLength = reasonLength;
        this.strengthLength = strengthLength;
        this.weaknessLength = weaknessLength;
        this.aspirationLength = aspirationLength;
    }
}
