package com.example.scheduler.lv6.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@ToString
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public boolean isMatchPassword(String password) {
        return this.password.equals(password);
    }
    public void updateName(String name){
        this.name = name;
    }
    public static User from(String name, String email, String password) {
        return new User(name, email, password);
    }

    public void delete(Long id) {
    }
}
