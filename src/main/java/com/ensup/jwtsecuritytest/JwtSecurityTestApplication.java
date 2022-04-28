package com.ensup.jwtsecuritytest;

import com.ensup.jwtsecuritytest.domain.User;
import com.ensup.jwtsecuritytest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class JwtSecurityTestApplication {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initUser(){
        List<User> users = Stream.of(
                new User(101,"toto",passwordEncoder.encode("123"),"titi@email.com"),
                new User(102,"tata",passwordEncoder.encode("123"),"titi@email.com"),
                new User(103,"tutu",passwordEncoder.encode("456"),"titi@email.com"),
                new User(104,"tete",passwordEncoder.encode("456"),"titi@email.com")
        ).collect(Collectors.toList());
        userRepository.saveAll(users);
    }

    public static void main(String[] args) {
        SpringApplication.run(JwtSecurityTestApplication.class, args);
    }

}
