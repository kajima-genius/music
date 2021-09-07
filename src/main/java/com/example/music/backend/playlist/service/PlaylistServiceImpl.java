package com.example.music.backend.playlist.service;

import com.example.music.backend.common.exception.NotFoundException;
import com.example.music.backend.playlist.converter.PlaylistMapper;
import com.example.music.backend.playlist.domain.Playlist;
import com.example.music.backend.playlist.dto.PlaylistDto;
import com.example.music.backend.playlist.exception.PlaylistNotExistException;
import com.example.music.backend.playlist.exception.VideoAlreadyExistsInPlaylistException;
import com.example.music.backend.playlist.repository.PlaylistRepository;
import com.example.music.backend.playlist.response.PlaylistResponse;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.video.response.YoutubeVideoResponse;
import com.example.music.backend.video.service.YoutubeVideoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final YoutubeVideoService videoService;

    @Override
    public List<PlaylistResponse> getPlaylistsByUserId(Long userId) {
        return PlaylistMapper.INSTANCE
                .listEntityToResponse(playlistRepository.findByOwnerId(userId));
    }

    @Override
    public PlaylistResponse getPlaylistById(Long playlistId) {
        return PlaylistMapper.INSTANCE.toResponse(playlistRepository.findById(playlistId)
                .orElseThrow(() -> new NotFoundException("Playlist with id: " + playlistId + " not found")));
    }

    @Override
    public PlaylistResponse create(PlaylistDto playlistDto, User user) {
        Playlist saved = PlaylistMapper.INSTANCE.toEntity(playlistDto, user);
        return PlaylistMapper.INSTANCE.toResponse(playlistRepository.save(saved));
    }

    @Override
    public void delete(Long id) {
        playlistRepository.deleteById(id);
    }

    @Override
    public void addVideo(Long playlistId, String youtubeId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new NotFoundException("Playlist with id: " + playlistId + " not found"));
        if (playlist.getListYoutubeId() == null) {
            throw new PlaylistNotExistException("playlist does not exist");
        }
        if (!playlist.getListYoutubeId().contains(youtubeId)) {
            playlist.getListYoutubeId().add(youtubeId);
            playlistRepository.save(playlist);
        }
        else {
            throw new VideoAlreadyExistsInPlaylistException("The video already exists in the playlist");
        }
    }

    @Override
    public void removeVideo(Long playlistId, String youtubeId) {
        playlistRepository.findById(playlistId)
                .orElseThrow(() -> new NotFoundException("Playlist with id: " + playlistId + " not found"))
                .getListYoutubeId().remove(youtubeId);
    }

    @Override
    public List<YoutubeVideoResponse> getAllVideoInPlaylist(Long playlistId) {
        return playlistRepository.findById(playlistId).get().getListYoutubeId().stream()
                .map(x -> videoService.getYoutubeVideo(x))
                .collect(Collectors.toList());
    }
}