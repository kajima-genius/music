package com.example.music.backend.playlist.dto;

import com.example.music.backend.video.dto.YoutubeVideoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlaylistDto {

    private Long id;
    private String name;
    private Long countVideos;
    private Long userId;
    private List<YoutubeVideoDto> videos = new ArrayList<>();

    public PlaylistDto(Long id, String name, Long countVideos, Long userId, List<YoutubeVideoDto> videos) {
        this.id = id;
        this.name = name;
        this.countVideos = countVideos;
        this.userId = userId;
        this.videos = videos;
    }
}
