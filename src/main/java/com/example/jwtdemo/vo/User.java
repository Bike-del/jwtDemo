package com.example.jwtdemo.vo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(unique = true)
    private String userName;

    private String password;

    //ROLE_USER
    private String role;

}
