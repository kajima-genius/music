package com.example.music.backend.playlist.domain;

import com.example.music.backend.user.domain.User;
import com.example.music.backend.video.domain.YoutubeVideo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long countVideos;

    @ManyToOne
    private User owner;

    @OneToMany
    private List<YoutubeVideo> videos;

    public Playlist(String name, Long countVideos, User owner, List<YoutubeVideo> videos) {
        this.name = name;
        this.countVideos = countVideos;
        this.owner = owner;
        this.videos = videos;
    }
}
