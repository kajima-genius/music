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
    private Long viewCount;
    private String title;
    private String channelTitle;
    private String description;
    private String imageUrl;

    public YoutubeVideo(String youtubeId, Long viewCount, String title, String channelTitle, String description, String imageUrl) {
        this.youtubeId = youtubeId;
        this.viewCount = viewCount;
        this.title = title;
        this.channelTitle = channelTitle;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        YoutubeVideo video = (YoutubeVideo) obj;
        return (this.id == video.id) && (this.youtubeId.equals(video.youtubeId));
    }
}
