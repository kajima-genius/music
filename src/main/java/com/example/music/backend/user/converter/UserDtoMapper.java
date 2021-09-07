package com.example.music.backend.user.converter;

import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "string")
public interface UserDtoMapper {
    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    @Mapping(target = "dateOfBirth", dateFormat = "dd/MM/yyyy")
    User toEntity(UserDto dto);
}
