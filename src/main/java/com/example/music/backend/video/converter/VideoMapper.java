package com.example.music.backend.video.converter;

import com.example.music.backend.video.response.YoutubeVideoResponse;
import com.google.api.services.youtube.model.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "string")
public interface VideoMapper {

    VideoMapper INSTANCE = Mappers.getMapper(VideoMapper.class);

    @Mapping(target = "youtubeId", source = "video.id")
    @Mapping(target = "channelId", source = "video.snippet.channelId")
    @Mapping(target = "title", source = "video.snippet().title")
    @Mapping(target = "imageUrl", source = "video.snippet.thumbnails.medium.url")
    @Mapping(target = "viewCount", source = "video.statistics.viewCount.longValue")
    @Mapping(target = "channelTitle", source = "video.snippet.channelTitle")
    @Mapping(target = "timestamp", source = "video.snippet.publishedAt")
    @Mapping(target = "description", source = "video.snippet.description")
    YoutubeVideoResponse toResponse(Video video);
}
