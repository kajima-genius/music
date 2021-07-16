package com.example.music.backend.user.converter;

import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.dto.UserDto;

public class UserDtoConverter {

    public static User toEntity(UserDto dto) {
        return new User(dto.getUserName(), dto.getGender(),
                dto.getEmail(), dto.getPassword(), dto.getRole(), dto.getDateOfBirch());
    }
}
