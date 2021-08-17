package com.example.music.backend.playlist.service;

import com.example.music.backend.playlist.dto.PlaylistDto;
import com.example.music.backend.playlist.response.PlaylistResponse;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.video.dto.YoutubeVideoDto;
import com.example.music.backend.video.response.YoutubeVideoResponse;

import java.util.List;

public interface PlaylistService {

    List<PlaylistResponse> getPlaylistsByUserId(Long userId);

    PlaylistResponse getPlaylistById(Long playlistId);

    PlaylistResponse create(PlaylistDto playlistDto, User user);

    List<YoutubeVideoResponse> getAllVideoInPlaylist(Long playlistId);

    void delete(Long id);

    void addVideo(Long playlistId, YoutubeVideoDto videoDto);

    void removeVideo(Long playlistId, YoutubeVideoDto videoDto);
}
