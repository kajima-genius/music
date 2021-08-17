package com.example.music.backend.playlist.response;

import com.example.music.backend.video.domain.YoutubeVideo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlaylistResponse {

    private Long id;
    private String name;
    private Long countVideos;
    private Long userId;
    private List<YoutubeVideo> videos;
}
