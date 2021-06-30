package com.example.music.backend.user.converter;

import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public User toEntity(UserDto dto) {
        return new User(dto.getUserName(), dto.getGender(),
                dto.getEmail(),dto.getPassword(),dto.getRole(), dto.getDateOfBirch());
    }

    public UserDto toDto(User entity) {
        return new UserDto(entity.getUserName(),entity.getGender(),
                entity.getEmail(), entity.getPassword(), entity.getDateOfBirth());
    }


}
