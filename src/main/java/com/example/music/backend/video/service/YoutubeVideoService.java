package com.example.music.backend.video.service;

import com.example.music.backend.video.response.YoutubeVideoResponse;

import java.util.List;

public interface YoutubeVideoService {

    List<YoutubeVideoResponse> searchYoutubeVideo(String queryTerm, Long maxResults);

    List<YoutubeVideoResponse> getYoutubeVideoTrends(Long maxResults);

    YoutubeVideoResponse getYoutubeVideo(String youtubeId);

    List<YoutubeVideoResponse> getYoutubeVideos(String multiplyId);
}
