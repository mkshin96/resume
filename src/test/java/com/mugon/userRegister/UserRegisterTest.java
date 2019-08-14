package com.mugon.userRegister;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mugon.domain.User;
import com.mugon.dto.UserDto;
import com.mugon.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRegisterTest {

    @Autowired
    UserRepository userRepository;

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    public void userRegisterTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId("testId");
        userDto.setPassword("testPassword");
        userDto.setEmail("test@gmail.com");

        //생성
        mockMvc.perform(post("/userRegister").content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf()))
                .andExpect(status().isCreated());

        //생성 확인
        User user = userRepository.findByIdx(2L);
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo("testId");
        assertThat(user.getEmail()).isEqualTo("test@gmail.com");

        //아이디 공백 시
        userDto.setId("");
        userDto.setPassword(passwordEncoder.encode("testPassword"));
        userDto.setEmail("test@gmail.com");

        mockMvc.perform(post("/userRegister").content(objectMapper.writeValueAsString(userDto)).contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf()))
                .andExpect(status().isBadRequest());

        //비밀번호 공백시
        mockMvc.perform(post("/userRegister").content(setUserAtObjectMapper(userDto, "testId", "", "test@gmail.com"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())).
                andExpect(status().isBadRequest());

        //이메일 형식이 안지켜질시
        mockMvc.perform(post("/userRegister").content(setUserAtObjectMapper(userDto, "testId", "testPassword", "test"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf()))
                .andExpect(status().isBadRequest());

    }


    //id, password, email값을 받아 json형식의 string형으로 반환
    public String setUserAtObjectMapper(UserDto userDto, String id, String password, String email) throws JsonProcessingException {
        userDto.setId(id);
        userDto.setPassword(password);
        userDto.setEmail(email);

        return objectMapper.writeValueAsString(userDto);
    }
}
