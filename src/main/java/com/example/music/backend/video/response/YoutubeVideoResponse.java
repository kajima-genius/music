package com.example.music.backend.video.response;

import com.google.api.client.util.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeVideoResponse {

    private String youtubeId;
    private String channelId;
    private String title;
    private String imageUrl;
    private Long viewCount;
    private String channelTitle;
    private DateTime timestamp;
    private String description;
}
