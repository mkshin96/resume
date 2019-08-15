package com.mugon.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mugon.domain.Projects;
import com.mugon.domain.User;
import com.mugon.dto.ProjectDto;
import com.mugon.dto.UserDto;
import com.mugon.repository.ProjectsRepository;
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
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private UserDetails userDetails;

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        //유저 생성
        UserDto userDto = new UserDto();
        userDto.setId("testId");
        userDto.setPassword("testPassword");
        userDto.setEmail("test@gmail.com");

        //회원 가입
        mockMvc.perform(post("/userRegister").content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf()))
                .andExpect(status().isCreated());

        //데이터베이스 확인
        User user = userRepository.findByIdx(2L);
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo("testId");
        assertThat(user.getEmail()).isEqualTo("test@gmail.com");

        //userdetails 등록
        userDetails = customUserDetailsService.loadUserByUsername("testId");

        //현재 로그인한 유저와 매핑
        mockMvc.perform(get("/projects").with(csrf()).with(user(userDetails)))
                .andExpect(status().isOk());
    }

    @Test
    public void 프로젝트_등록() throws Exception {
        ProjectDto projectDto = new ProjectDto();

        //프로젝트 생성
        projectDto.setName("테스트 프로젝트");
        projectDto.setDescription("이것은 테스트입니다.");
        projectDto.setPeriod("20190814");
        projectDto.setPersons("1");

        //프로젝트 등록
        mockMvc.perform(post("/projects").content(objectMapper.writeValueAsString(projectDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .with(user(userDetails)))
                .andExpect(status().isCreated());

        //데이터베이스 확인
        Projects projects = projectsRepository.findByIdx(1L);
        assertThat(projects).isNotNull();
        assertThat(projects.getName()).isEqualTo("테스트 프로젝트");
        assertThat(projects.getDescription()).isEqualTo("이것은 테스트입니다.");

        //로그인한 유저와 매핑확인
        assertThat(projects.getUser().getId()).isEqualTo("testId");
        assertThat(projects.getUser().getEmail()).isEqualTo("test@gmail.com");
        assertThat(projects.getUser().getProjectsSet().toArray()[0]).isEqualTo(projects);

        //프로젝트 이름이 ""일 경우
        projectDto.setName("");

        //프로젝트 등록
        mockMvc.perform(post("/projects").content(objectMapper.writeValueAsString(projectDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .with(user(userDetails)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void 프로젝트_수정() throws Exception {

        //프로젝트 생성
        ProjectDto projectDto = new ProjectDto();
        projectDto.setName("테스트 프로젝트");
        projectDto.setDescription("이것은 테스트입니다.");

        //프로젝트 등록
        mockMvc.perform(post("/projects").content(objectMapper.writeValueAsString(projectDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf())
                        .with(user(userDetails)))
                        .andExpect(status().isCreated());

        //데이터베이스 확인
        Projects projects = projectsRepository.findByIdx(1L);
        assertThat(projects).isNotNull();
        assertThat(projects.getName()).isEqualTo("테스트 프로젝트");
        assertThat(projects.getDescription()).isEqualTo("이것은 테스트입니다.");

        //프로젝트 수정
        projectDto.setName("수정한 테스트");
        projectDto.setDescription("수정한 테스트입니다.");

        //프로젝트 등록
        mockMvc.perform(put("/projects/1").content(objectMapper.writeValueAsString(projectDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf())
                        .with(user(userDetails)))
                        .andExpect(status().isOk());

        //데이터베이스 확인
        projects = projectsRepository.findByIdx(1L);
        assertThat(projects.getName()).isEqualTo("수정한 테스트");
        assertThat(projects.getDescription()).isEqualTo("수정한 테스트입니다.");
    }

    @Test
    public void 프로젝트_삭제() throws Exception {

        //프로젝트 생성
        ProjectDto projectDto = new ProjectDto();
        projectDto.setName("테스트 프로젝트");
        projectDto.setDescription("이것은 테스트입니다.");

        //프로젝트 등록
        mockMvc.perform(post("/projects").content(objectMapper.writeValueAsString(projectDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf())
                        .with(user(userDetails)))
                        .andExpect(status().isCreated());

        //데이터베이스 확인
        Projects projects = projectsRepository.findByIdx(1L);
        assertThat(projects).isNotNull();
        assertThat(projects.getName()).isEqualTo("테스트 프로젝트");
        assertThat(projects.getDescription()).isEqualTo("이것은 테스트입니다.");

        //프로젝트 삭제
        mockMvc.perform(delete("/projects/1").with(csrf()).with(user(userDetails)))
                .andExpect(status().isOk());

        //데이터베이스 확인
        projects = projectsRepository.findByIdx(1L);
        assertThat(projects).isNull();


    }
}
