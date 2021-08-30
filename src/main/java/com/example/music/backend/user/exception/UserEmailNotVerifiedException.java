package com.example.music.backend.user.exception;

public class UserEmailNotVerifiedException extends RuntimeException{

    public UserEmailNotVerifiedException(String message) {
        super(message);
    }
}
