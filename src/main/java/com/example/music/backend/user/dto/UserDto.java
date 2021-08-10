package com.example.music.backend.user.dto;

import com.example.music.backend.user.domain.Gender;
import com.example.music.backend.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String username;
    private Gender gender;
    private Long id;
    private String email;
    private String password;
    private Role role;
    private String dateOfBirth;

    public UserDto(String userName, Gender gender, String email, String password, String dateOfBirch, Long id) {
        this.username = userName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirch;
        this.id = id;
    }
}