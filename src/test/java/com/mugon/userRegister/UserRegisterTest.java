package com.mugon.userRegister;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mugon.domain.User;
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
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


    }

    @Test
    public void userRegisterTest() throws Exception {
        User user = new User();
        user.setId("testId");
        user.setPassword(passwordEncoder.encode("testPassword"));
        user.setEmail("test@gmail.com");

        //생성
        mockMvc.perform(post("/userRegister").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        //생성 확인
        user = userRepository.findById(user.getId());
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo("testId");
        assertThat(user.getEmail()).isEqualTo("test@gmail.com");

        //아이디 공백 시
        user.setId("");
        user.setPassword(passwordEncoder.encode("testPassword"));
        user.setEmail("test@gmail.com");

        mockMvc.perform(post("/userRegister").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        //비밀번호 공백시
        mockMvc.perform(post("/userRegister").content(setUserAtObjectMapper(user, "testId", "", "test@gmail.com"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());

        //이메일 형식이 안지켜질시
        mockMvc.perform(post("/userRegister").content(setUserAtObjectMapper(user, "testId", "testPassword", "test"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());

    }


    //id, password, email값을 받아 json형식의 string형으로 반환
    public String setUserAtObjectMapper(User user, String id, String password, String email) throws JsonProcessingException {
        user.setId(id);
        user.setPassword(password);
        user.setEmail(email);

        return objectMapper.writeValueAsString(user);
    }
}
