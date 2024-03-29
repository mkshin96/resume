package com.mugon.repository;

import com.mugon.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(String id);
    User findByEmail(String email);
    User findByIdx(Long idx);
}
