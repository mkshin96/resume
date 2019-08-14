package com.mugon.introduction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mugon.domain.Introduction;
import com.mugon.domain.User;
import com.mugon.dto.IntroductionDto;
import com.mugon.dto.UserDto;
import com.mugon.repository.IntroductionRepository;
import com.mugon.repository.UserRepository;
import com.mugon.service.CustomUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntroductionTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private UserDetails userDetails;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private IntroductionRepository introductionRepository;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        //유저 생성
        UserDto userDto = new UserDto();
        userDto.setId("testId");
        userDto.setPassword("testPassword");
        userDto.setEmail("test@gmail.com");

        //회원 가입
        mockMvc.perform(post("/userRegister").content(objectMapper.writeValueAsString(userDto)).contentType(MediaType.APPLICATION_JSON_VALUE)
            .with(csrf()))
            .andExpect(status().isCreated());

        //데이터베이스 확인
        User user = userRepository.findByIdx(2L);
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo("testId");
        assertThat(user.getEmail()).isEqualTo("test@gmail.com");

        //userdetails 등록
        userDetails = customUserDetailsService.loadUserByUsername("testId");

        //등록한 userdeatils 확인
        assertThat(userDetails.getUsername()).isEqualTo("testId");
        assertThat(userDetails.isEnabled()).isEqualTo(true);

        //현재 유저와 매핑
        mockMvc.perform(get("/introduction")
                .with(csrf())
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(authenticated());

    }

    @Test
    public void 자기소개서_등록() throws Exception {

        //자기소개서 등록 화면
        mockMvc.perform(get("/introduction/register")
                .with(csrf())
                .with(user(userDetails)))
                .andExpect(status().isOk());

        //자기소개서 생성
        IntroductionDto introductionDto = new IntroductionDto();
        introductionDto.setTitle("test title");
        introductionDto.setGrowth("test growth");

        //자기소개서 등록
        mockMvc.perform(post("/introduction").content(objectMapper.writeValueAsString(introductionDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf())
                        .with(user(userDetails)))
                        .andExpect(status().isCreated());

        //데이터베이스 확인
        Introduction introduction = introductionRepository.findByIdx(1L);

        assertThat(introduction).isNotNull();
        assertThat(introduction.getTitle()).isEqualTo("test title");
        assertThat(introduction.getGrowth()).isEqualTo("test growth");
        assertThat(introduction.getAspiration()).isNull();

        //유저와 매핑 확인
        assertThat(introduction.getUser().getId()).isEqualTo("testId");

        //타이틀이 ""일 경우
        introductionDto.setTitle("");

        //등록 확인
        mockMvc.perform(post("/introduction").content(objectMapper.writeValueAsString(introductionDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .with(user(userDetails)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 자기소개서_수정() throws Exception {

        //자기소개서 생성
        IntroductionDto introductionDto = new IntroductionDto();
        introductionDto.setTitle("test title");
        introductionDto.setGrowth("test growth");

        //자기소개서 등록
        mockMvc.perform(post("/introduction").content(objectMapper.writeValueAsString(introductionDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .with(user(userDetails)))
                .andExpect(status().isCreated());

        //자기소개서 수정
        introductionDto.setTitle("modify title");
        introductionDto.setGrowth("modify growth");

        //수정한 자기소개서 등록
        mockMvc.perform(put("/introduction/1").content(objectMapper.writeValueAsString(introductionDto))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .with(csrf())
                    .with(user(userDetails)));

        //데이터베이스 확인
        Introduction introduction = introductionRepository.findByIdx(1L);
        assertThat(introduction.getTitle()).isEqualTo("modify title");
        assertThat(introduction.getGrowth()).isEqualTo("modify growth");

        //타이플을 ""로 수정
        introductionDto.setTitle("");

        mockMvc.perform(put("/introduction/1").content(objectMapper.writeValueAsString(introductionDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .with(user(userDetails)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 자기소개서_삭제() throws Exception {

        //자기소개서 생성
        IntroductionDto introductionDto = new IntroductionDto();
        introductionDto.setTitle("test title");
        introductionDto.setGrowth("test growth");

        //자기소개서 등록
        mockMvc.perform(post("/introduction").content(objectMapper.writeValueAsString(introductionDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .with(user(userDetails)))
                .andExpect(status().isCreated());

        //자기소개서 삭제
        mockMvc.perform(delete("/introduction/1").with(csrf()).with(user(userDetails)));

        //데이터베이스 확인
        Introduction introduction = introductionRepository.findByIdx(1L);
        assertThat(introduction).isNull();
    }


}
