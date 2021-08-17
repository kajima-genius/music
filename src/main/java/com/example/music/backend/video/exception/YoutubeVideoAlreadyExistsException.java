package com.example.music.backend.video.exception;

public class YoutubeVideoAlreadyExistsException extends RuntimeException {

    public YoutubeVideoAlreadyExistsException(String message) {
        super(message);
    }
}
