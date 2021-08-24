package com.example.music.backend.video.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class YoutubeVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String youtubeId;

    public YoutubeVideo(String youtubeId) {
        this.youtubeId = youtubeId;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        YoutubeVideo video = (YoutubeVideo) obj;
        return (this.youtubeId.equals(video.youtubeId));
    }
}
