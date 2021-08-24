package com.example.music.backend.video.service;

import com.example.music.backend.video.dto.YoutubeVideoDto;
import com.example.music.backend.video.response.YoutubeVideoResponse;

import java.util.List;

public interface YoutubeVideoService {

    List<YoutubeVideoResponse> searchYoutubeVideo(String queryTerm, Long maxResults);

    List<YoutubeVideoResponse> getYoutubeVideoTrends(Long maxResults);

    void addNewVideo(YoutubeVideoDto youtubeVideoDto);

    YoutubeVideoResponse getYoutubeVideo(String youtubeId);

    void delete(String youtubeId);
}
