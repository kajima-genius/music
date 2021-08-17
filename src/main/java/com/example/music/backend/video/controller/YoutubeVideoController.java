package com.example.music.backend.video.controller;

import com.example.music.backend.video.response.YoutubeVideoResponse;
import com.example.music.backend.video.service.YoutubeVideoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin("http://localhost:3000")
@RestController
@AllArgsConstructor
@RequestMapping("/videos")
public class YoutubeVideoController {

    private final YoutubeVideoService youtubeVideoService;

    @GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<YoutubeVideoResponse>>  getSearchResult(@RequestParam(value = "queryTerm", defaultValue = "trends") String queryTerm) {
        List<YoutubeVideoResponse> results = youtubeVideoService.searchYoutubeVideo(queryTerm);
        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/trends", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<YoutubeVideoResponse>>  getYoutubeTrends() {
        List<YoutubeVideoResponse> results = youtubeVideoService.getYoutubeVideoTrends();
        return ResponseEntity.ok(results);
    }
}
