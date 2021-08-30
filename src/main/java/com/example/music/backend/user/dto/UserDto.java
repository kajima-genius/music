package com.example.music.backend.user.dto;

import com.example.music.backend.user.domain.Gender;
import com.example.music.backend.user.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String userName;
    private Gender gender;
    private Long id;
    private String email;
    private String password;
    private Role role = Role.USER;
    private String dateOfBirch;

    public UserDto(String userName, Gender gender, String email, String password, String dateOfBirch) {
        this.userName = userName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.dateOfBirch = dateOfBirch;
    }
}