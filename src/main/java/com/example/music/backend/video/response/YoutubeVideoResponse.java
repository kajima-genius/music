package com.example.music.backend.video.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class YoutubeVideoResponse {

    private String youtubeId;
    private String channelId;
    private String title;
    private String imageUrl;
    private Long viewCount;
    private String channelTitle;
    private Long timestamp;

    public YoutubeVideoResponse(String youtubeId, String channelId, String title, String imageUrl, Long viewCount, String channelTitle, Long timestamp) {
        this.youtubeId = youtubeId;
        this.channelId = channelId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.viewCount = viewCount;
        this.channelTitle = channelTitle;
        this.timestamp = timestamp;
    }
}
