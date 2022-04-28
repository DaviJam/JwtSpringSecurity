package com.ensup.jwtsecuritytest.repository;

import com.ensup.jwtsecuritytest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
