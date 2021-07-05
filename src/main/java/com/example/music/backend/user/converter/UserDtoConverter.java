package com.example.music.backend.user.converter;

import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.dto.UserDto;

public class UserDtoConverter {

    public static User toEntity(UserDto dto) {
        return new User(dto.getUserName(), dto.getGender(),
                dto.getEmail(),dto.getPassword(),dto.getRole(), dto.getDateOfBirch());
    }

    public static UserDto toDto(User entity) {
        return new UserDto(entity.getUserName(),entity.getGender(),
                entity.getEmail(), entity.getPassword(), entity.getDateOfBirth());
    }


}
