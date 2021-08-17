package com.example.music.backend.playlist.converter;

import com.example.music.backend.playlist.domain.Playlist;
import com.example.music.backend.playlist.dto.PlaylistDto;
import com.example.music.backend.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "string")
public interface PlaylistDtoMapper {

    PlaylistDtoMapper INSTANCE = Mappers.getMapper(PlaylistDtoMapper.class);

    @Mapping(target = "owner", source = "user")
    @Mapping(target = "id", expression = "java(dto.getId())")
    Playlist toEntity(PlaylistDto dto, User user);

//    @Mapping(target = "userId", expression = "java(entity.getOwner().getId())")
//    PlaylistDto toDto(Playlist entity);
//
//    List<PlaylistDto> listEntityToListDto(List<Playlist> entities);
}
