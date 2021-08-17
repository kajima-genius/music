package com.example.music.backend.video.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YoutubeVideoDto {

    private Long id;
    private String youtubeId;
    private String title;

    public YoutubeVideoDto(String youtubeId, String title, String link) {
        this.youtubeId = youtubeId;
        this.title = title;
    }
}
