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
    private Role role = Role.USER;
    private String dateOfBirth;
}