package com.ensup.jwtsecuritytest.controller;

import com.ensup.jwtsecuritytest.domain.AuthRequest;
import com.ensup.jwtsecuritytest.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
public class WelcomeController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/")
    public String home(){
        return "Welcome to ensup";
    }

    @PostMapping("/auth")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        } catch (Exception e){
            throw new Exception("invalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> ExceptionHandler(Exception exception){
        ResponseEntity<String> entity = new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }

}
