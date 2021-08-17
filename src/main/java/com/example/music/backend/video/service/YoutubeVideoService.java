package com.example.music.backend.video.service;

import com.example.music.backend.video.dto.YoutubeVideoDto;
import com.example.music.backend.video.response.YoutubeVideoResponse;

import java.util.List;

public interface YoutubeVideoService {

    List<YoutubeVideoResponse> searchYoutubeVideo(String queryTerm);

    List<YoutubeVideoResponse> getYoutubeVideoTrends();

    YoutubeVideoResponse addNewVideo(YoutubeVideoDto youtubeVideoDto);

    YoutubeVideoResponse getYoutubeVideo(String youtubeId);

    void delete(Long id);
}
