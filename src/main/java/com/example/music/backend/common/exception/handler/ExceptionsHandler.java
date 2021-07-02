package com.example.music.backend.common.exception.handler;


import com.example.music.backend.common.exception.NotFoundException;
import com.example.music.backend.common.exception.wrapper.ExceptionWrapper;
import com.example.music.backend.user.exception.UserAlreadyExistException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ExceptionWrapper handleNotFoundException(NotFoundException exception) {
        return new ExceptionWrapper(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionWrapper handleUserAlreadyExistsException(UserAlreadyExistException exception) {
        return new ExceptionWrapper(BAD_REQUEST, exception.getMessage());
    }

}
