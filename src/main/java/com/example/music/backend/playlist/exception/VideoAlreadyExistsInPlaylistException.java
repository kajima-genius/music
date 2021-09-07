package com.example.music.backend.playlist.exception;

public class VideoAlreadyExistsInPlaylistException extends RuntimeException {

    public VideoAlreadyExistsInPlaylistException(String message) {
        super(message);
    }
}
