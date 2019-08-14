package com.mugon.dto;

import com.mugon.domain.Introduction;
import com.mugon.domain.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class IntroductionDto {

    @NotBlank(message = "필수항목입니다.")
    private String title;

    //성장배경
    private String growth;

    //지원동기
    private String reason;

    //장점
    private String strength;

    //단점
    private String weakness;

    //입사 후 포부
    private String aspiration;

    private LocalDateTime registeredDate;

    public Introduction setIntroduction(User currentUser) {
        Introduction introduction = new Introduction();

        introduction.setTitle(this.title);
        introduction.setGrowth(this.growth);
        introduction.setReason(this.reason);
        introduction.setStrength(this.strength);
        introduction.setWeakness(this.weakness);
        introduction.setAspiration(this.aspiration);
        introduction.setUsers(currentUser);

        return introduction;
    }
}
