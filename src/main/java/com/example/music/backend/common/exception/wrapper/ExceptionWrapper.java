package com.example.music.backend.common.exception.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class ExceptionWrapper {

    private int statusCode;
    private String message;
    private List<String> errors;

    public ExceptionWrapper(HttpStatus status, String message) {
        this.statusCode = status.value();
        this.message = message;
    }

    public ExceptionWrapper(HttpStatus status, List<String> errors) {
        this.statusCode = status.value();
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public List<String> getErrors() {
        return errors;
    }
}
