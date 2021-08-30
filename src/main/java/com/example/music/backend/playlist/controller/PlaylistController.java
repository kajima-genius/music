package com.example.music.backend.playlist.controller;

import com.example.music.backend.playlist.dto.PlaylistDto;
import com.example.music.backend.playlist.response.PlaylistResponse;
import com.example.music.backend.playlist.service.PlaylistService;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.service.UserService;
import com.example.music.backend.video.dto.YoutubeVideoDto;
import com.example.music.backend.video.response.YoutubeVideoResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final UserService userService;

    @PutMapping(value = "/{playlistId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<YoutubeVideoResponse>> addVideoToPlaylist(
            @PathVariable("playlistId") Long playlistId,
            @RequestBody YoutubeVideoDto dto) {

        playlistService.addVideo(playlistId, dto.getYoutubeId());
        List<YoutubeVideoResponse> videos = playlistService.getAllVideoInPlaylist(playlistId);
        return ResponseEntity.ok(videos);
    }

    @DeleteMapping(value = "/{playlistId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<YoutubeVideoResponse>> removeVideoFromPlaylist(
            @PathVariable("playlistId") Long playlistId,
            String youtubeId) {

        playlistService.removeVideo(playlistId, youtubeId);
        List<YoutubeVideoResponse> videos = playlistService.getAllVideoInPlaylist(playlistId);
        return ResponseEntity.ok(videos);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PlaylistResponse> create(@RequestBody PlaylistDto playlistDto) {
        Long userId = userService.getUserByEmail(
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName())
                .getId();
        User user = new User();
        user.setId(userId);
        PlaylistResponse response = playlistService.create(playlistDto, user);
        return ResponseEntity.created(URI.create("/playlists" + response.getId())).body(response);
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<PlaylistResponse>> getAllPlaylists() {
        Long userId = userService.getUserByEmail(
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName())
                .getId();
        List<PlaylistResponse> response = playlistService.getPlaylistsByUserId(userId);
        return ResponseEntity.ok(response);
    }
}
