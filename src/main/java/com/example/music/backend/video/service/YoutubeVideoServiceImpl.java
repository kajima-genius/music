package com.example.music.backend.video.service;

import com.example.music.backend.common.exception.NotFoundException;
import com.example.music.backend.video.converter.YoutubeVideoDtoMapper;
import com.example.music.backend.video.converter.YoutubeVideoResponseMapper;
import com.example.music.backend.video.domain.YoutubeVideo;
import com.example.music.backend.video.dto.YoutubeVideoDto;
import com.example.music.backend.video.repository.YoutubeVideoRepository;
import com.example.music.backend.video.response.YoutubeVideoResponse;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class YoutubeVideoServiceImpl implements YoutubeVideoService {

    private static final Long MAX_SEARCH_RESULTS = 50L;
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final Long MAX_TRENDS_VIDEOS = 20L;

    private final YoutubeVideoRepository youtubeVideoRepository;
    private YouTube youtube = getService();

    public boolean videoExist(String youtubeId) {
        return youtubeVideoRepository.findByYoutubeId(youtubeId).isPresent();
    }

    private YouTube getService() {
        return new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) {
            }
        }).setApplicationName("YoutubeVideo")
                .setYouTubeRequestInitializer(new YouTubeRequestInitializer("AIzaSyC5k0VruOYaTht90TTecW237lLLgOQ7s4g")).build();
    }

    private YoutubeVideoResponse save(YoutubeVideoDto youtubeVideoDto) {
        YoutubeVideo newVideo = YoutubeVideoDtoMapper.INSTANCE.toEntity(youtubeVideoDto);
        return YoutubeVideoResponseMapper.INSTANCE.toResponse(youtubeVideoRepository.save(newVideo));
    }

    private List<YoutubeVideoResponse> fromYoutubeSearchFormatToResponse(Iterator<SearchResult> iterator) {
        List<YoutubeVideoResponse> results = new ArrayList<>();

        while (iterator.hasNext()) {
            SearchResult singleVideo = iterator.next();
            results.add(new YoutubeVideoResponse(
                    singleVideo.getId().getVideoId(),
                    singleVideo.getSnippet().getChannelId(),
                    singleVideo.getSnippet().getTitle(),
                    singleVideo.getSnippet().getThumbnails().getMedium().getUrl(),
                    1L,
                    singleVideo.getSnippet().getChannelTitle(),
                    singleVideo.getSnippet().getPublishedAt().getValue()));
        }

        return results;
    }

    private List<YoutubeVideoResponse> fromYoutubeFormatToResponse(Iterator<Video> iterator) {
        List<YoutubeVideoResponse> results = new ArrayList<>();

        while (iterator.hasNext()) {
            Video singleVideo = iterator.next();
            results.add(new YoutubeVideoResponse(
                    singleVideo.getId(),
                    singleVideo.getSnippet().getChannelId(),
                    singleVideo.getSnippet().getTitle(),
                    singleVideo.getSnippet().getThumbnails().getMedium().getUrl(),
                    singleVideo.getStatistics().getViewCount().longValue(),
                    singleVideo.getSnippet().getChannelTitle(),
                    singleVideo.getSnippet().getPublishedAt().getValue()));
        }
        return results;
    }

    @Override
    public List<YoutubeVideoResponse> searchYoutubeVideo(String queryTerm) {
        try {
            YouTube.Search.List request = youtube.search().list("id,snippet");

            SearchListResponse response = request
                    .setQ(queryTerm)
                    .setType("video")
                    .setMaxResults(MAX_SEARCH_RESULTS)
                    .execute();

            return fromYoutubeSearchFormatToResponse(response.getItems().iterator());

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    @Override
    public List<YoutubeVideoResponse> getYoutubeVideoTrends() {
        try {
            YouTube.Videos.List request = youtube.videos()
                    .list("snippet,contentDetails,statistics");
            VideoListResponse response = request.setChart("mostPopular")
                    .setRegionCode("US")
                    .setMaxResults(MAX_TRENDS_VIDEOS)
                    .execute();
            return fromYoutubeFormatToResponse(response.getItems().iterator());
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    @Override
    public YoutubeVideoResponse addNewVideo(YoutubeVideoDto youtubeVideoDto) {
        if (videoExist(youtubeVideoDto.getYoutubeId()))
            return YoutubeVideoResponseMapper.INSTANCE.toResponse(YoutubeVideoDtoMapper.INSTANCE.toEntity(youtubeVideoDto));
        else return save(youtubeVideoDto);
    }

    @Override
    public YoutubeVideoResponse getYoutubeVideo(String youtubeId) {
        return YoutubeVideoResponseMapper.INSTANCE.toResponse(youtubeVideoRepository.findByYoutubeId(youtubeId)
                .orElseThrow(() -> new NotFoundException("Video with id: " + youtubeId + "not found")));
    }

    @Override
    public void delete(Long id) {
        youtubeVideoRepository.deleteById(id);
    }
}
