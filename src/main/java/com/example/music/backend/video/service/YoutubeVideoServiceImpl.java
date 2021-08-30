package com.example.music.backend.video.service;

import com.example.music.backend.video.converter.VideoYoutubeVideoResponseMapper;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class YoutubeVideoServiceImpl implements YoutubeVideoService {

    @Value("${youtube.key}")
    private String YOUTUBE_API_KEY;

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    private YouTube youtube;

    @PostConstruct
    private void initializationYoutube() {
        this.youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) {
            }
        }).setApplicationName("YoutubeVideo")
                .setYouTubeRequestInitializer(new YouTubeRequestInitializer(YOUTUBE_API_KEY)).build();
    }

    private List<YoutubeVideoResponse> fromYoutubeSearchFormatToResponse(List<SearchResult> searchResults) throws IOException {
        List<YoutubeVideoResponse> results = new ArrayList<>();

        List<YoutubeVideoResponse> responses = searchResults.stream().map(
                x -> getYoutubeVideo(x.getId().getVideoId()))
                .collect(Collectors.toList());

        Iterator<YoutubeVideoResponse> iterator = responses.iterator();

        while (iterator.hasNext()) {
            String description = searchResults.iterator().next().getSnippet().getDescription();
            YoutubeVideoResponse addedVideo = iterator.next();
            addedVideo.setDescription(description);
            results.add(addedVideo);
        }

        return results;
    }

    private List<YoutubeVideoResponse> fromYoutubeFormatToResponse(Iterator<Video> iterator) {
        List<YoutubeVideoResponse> results = new ArrayList<>();

        while (iterator.hasNext()) {
            Video singleVideo = iterator.next();
            YoutubeVideoResponse addedVideo = VideoYoutubeVideoResponseMapper
                    .INSTANCE.toResponse(singleVideo);
            results.add(addedVideo);
        }
        return results;
    }

    @Override
    public List<YoutubeVideoResponse> searchYoutubeVideo(String queryTerm, Long maxResults) {
        try {
            YouTube.Search.List request = youtube.search().list("id,snippet");

            SearchListResponse response = request
                    .setQ(queryTerm)
                    .setType("video")
                    .setMaxResults(maxResults)
                    .execute();

            return fromYoutubeSearchFormatToResponse(response.getItems());

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    @Override
    public List<YoutubeVideoResponse> getYoutubeVideoTrends(Long maxResults) {
        try {
            YouTube.Videos.List request = youtube.videos()
                    .list("snippet,contentDetails,statistics");
            VideoListResponse response = request.setChart("mostPopular")
                    .setRegionCode("US")
                    .setMaxResults(maxResults)
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
    public YoutubeVideoResponse getYoutubeVideo(String youtubeId) {
        try {
            Video video = youtube.videos()
                    .list("snippet,contentDetails,statistics")
                    .setId(youtubeId)
                    .execute()
                    .getItems().get(0);
            return VideoYoutubeVideoResponseMapper.INSTANCE.toResponse(video);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
