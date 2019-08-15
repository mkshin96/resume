package com.mugon.repository;

import com.mugon.domain.Projects;
import com.mugon.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectsRepository extends JpaRepository<Projects, Long> {
    List<Projects> findByUser(User user);
    Projects findByIdx(Long idx);
}
