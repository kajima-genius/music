package com.example.music.backend.user.dto;

import com.example.music.backend.user.domain.Gender;
import com.example.music.backend.user.domain.Role;

import java.util.Date;

public class UserDto {

    private String userName;
    private Gender gender;
    private Long id;
    private String email;
    private String password;
    private Role role = Role.USER;
    private String dateOfBirch;

    public UserDto() {
    }

    public UserDto(String userName, Gender gender, String email, String password, String dateOfBirch) {
        this.userName = userName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.dateOfBirch = dateOfBirch;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirch() {
        return dateOfBirch;
    }

    public void setDateOfBirch(String dateOfBirch) {
        this.dateOfBirch = dateOfBirch;
    }


}