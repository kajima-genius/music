package com.example.music.backend.video.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YoutubeVideoDto {

    private String youtubeId;

    public YoutubeVideoDto(String youtubeId) {
        this.youtubeId = youtubeId;
    }
}
