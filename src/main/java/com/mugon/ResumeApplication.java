package com.mugon;

import com.mugon.domain.User;
import com.mugon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ResumeApplication {

    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ResumeApplication.class, args);
    }

    //테스트용
    @Bean
    public CommandLineRunner runner(UserRepository userRepository){
        return (args) -> {
            userRepository.save(User.builder().id("test").password(passwordEncoder.encode("test")).email("test@gmail.com").build());
        };
    }
}
