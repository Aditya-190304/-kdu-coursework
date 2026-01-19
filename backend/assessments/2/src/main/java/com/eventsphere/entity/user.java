package com.eventsphere.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    String password;
private String User_Role;

public Long getId() { return id; }
public String getUsername() { return username; }
public String getPassword() { return password; }
public String getRole() { return User_Role; }
    public void setRole(String role) { this.User_Role = role; }
}