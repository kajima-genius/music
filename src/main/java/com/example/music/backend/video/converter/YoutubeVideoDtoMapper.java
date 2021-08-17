package com.example.music.backend.video.converter;

import com.example.music.backend.video.domain.YoutubeVideo;
import com.example.music.backend.video.dto.YoutubeVideoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "string")
public interface YoutubeVideoDtoMapper {
    YoutubeVideoDtoMapper INSTANCE = Mappers.getMapper(YoutubeVideoDtoMapper.class);

    YoutubeVideo toEntity(YoutubeVideoDto dto);
}
