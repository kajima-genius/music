package com.example.music.backend.common.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException (String message) {
        super(message);
    }
}
