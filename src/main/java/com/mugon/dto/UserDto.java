package com.mugon.dto;

import com.mugon.domain.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @NotBlank(message = "필수항목입니다.")
    @Size(min = 4, max = 16, message = "필수항목입니다.")
    private String id;

    @NotBlank(message = "필수항목입니다.")
    @Size(min = 6, max = 16, message = "필수항목입니다.")
    private String password;

    @NotBlank(message = "필수항목입니다.")
    @Email(message = "이메일 양식을 지켜주세요.")
    private String email;


    public User setUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        return user;
    }
}
