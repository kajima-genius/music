package com.example.music.backend.playlist.domain;

import com.example.music.backend.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long countVideos;

    @ManyToOne
    private User owner;

    @ElementCollection
    @CollectionTable(name = "videos")
    @Column(name = "youtubeId")
    private List<String> listYoutubeId = new ArrayList<>();

    public Long getCountVideos() {
        return listYoutubeId.stream().count();
    }
}
