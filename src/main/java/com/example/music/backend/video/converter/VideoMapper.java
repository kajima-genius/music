package com.example.music.backend.video.converter;

import com.example.music.backend.video.response.YoutubeVideoResponse;
import com.google.api.services.youtube.model.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "string")
public interface VideoMapper {

    VideoMapper INSTANCE = Mappers.getMapper(VideoMapper.class);

    @Mapping(target = "youtubeId", expression = "java(video.getId())")
    @Mapping(target = "channelId", expression = "java(video.getSnippet().getChannelId())")
    @Mapping(target = "title", expression = "java(video.getSnippet().getTitle())")
    @Mapping(target = "imageUrl", expression = "java(video.getSnippet().getThumbnails().getMedium().getUrl())")
    @Mapping(target = "viewCount", expression = "java(video.getStatistics().getViewCount().longValue())")
    @Mapping(target = "channelTitle", expression = "java(video.getSnippet().getChannelTitle())")
    @Mapping(target = "timestamp", expression = "java(video.getSnippet().getPublishedAt())")
    @Mapping(target = "description", expression = "java(video.getSnippet().getDescription())")
    YoutubeVideoResponse toResponse(Video video);
}
