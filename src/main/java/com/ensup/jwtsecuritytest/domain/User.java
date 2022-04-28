package com.ensup.jwtsecuritytest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    private int id;
    private String username;
    private String password;
    private String email;
}
