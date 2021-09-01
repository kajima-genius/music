package com.example.music.backend.playlist.exception;

public class PlaylistNotExistException extends RuntimeException{

    public PlaylistNotExistException(String message) {
        super(message);
    }
}
