package com.mugon.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mugon.domain.User;
import com.mugon.repository.UserRepository;
import com.mugon.service.CustomUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Before
    public void setUp(){
        User user = new User();
        user.setId("testId");
        user.setPassword(passwordEncoder.encode("testPassword"));
        user.setEmail("test@gmail.com");

        userRepository.save(user);

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

    }

    @Test
    public void loginTest() throws Exception {
        //로그인 id 실패
        mockMvc.perform(post("/login").param("id", "testId1").param("password", "testPassword").with(csrf()))
                .andExpect(unauthenticated()).andExpect(redirectedUrl("/login?error")).andExpect(status().is3xxRedirection());

        //로그인 password 실패
        mockMvc.perform(post("/login").param("id", "testId").param("password", "testPassword1").with(csrf()))
                .andExpect(unauthenticated()).andExpect(redirectedUrl("/login?error")).andExpect(status().is3xxRedirection());

        //로그인 성공
        mockMvc.perform(post("/login").param("id", "testId").param("password", "testPassword").with(csrf()))
                .andExpect(authenticated()).andExpect(forwardedUrl("/login/success")).andExpect(status().is2xxSuccessful());

        //권한이 없는 사용자
        mockMvc.perform(get("/main")).andExpect(redirectedUrl("http://localhost/login")).andExpect(status().is3xxRedirection());

        UserDetails userDetails = userDetailsService.loadUserByUsername("testId");

        //권한이 있는 사용자
        mockMvc.perform(post("/login/success").with(user(userDetails)).with(csrf())).andExpect(redirectedUrl("/main"))
                .andExpect(status().is3xxRedirection());
    }
}
