package com.example.music.backend.playlist.converter;

import com.example.music.backend.playlist.domain.Playlist;
import com.example.music.backend.playlist.dto.PlaylistDto;
import com.example.music.backend.playlist.response.PlaylistResponse;
import com.example.music.backend.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "string")
public interface PlaylistMapper {

    PlaylistMapper INSTANCE = Mappers.getMapper(PlaylistMapper.class);

    @Mapping(target = "owner", source = "user")
    @Mapping(target = "id", ignore = true)
    Playlist toEntity(PlaylistDto dto, User user);

    @Mapping(target = "ownerId", source = "entity.owner.id")
    PlaylistResponse toResponse(Playlist entity);

    List<PlaylistResponse> listEntityToResponse(List<Playlist> entities);
}
