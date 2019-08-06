package com.mugon.repository;

import com.mugon.domain.Introduction;
import com.mugon.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntroductionRepository extends JpaRepository<Introduction, Long> {
    List<Introduction> findByUser(User user);
}
