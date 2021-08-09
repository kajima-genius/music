package com.example.music.backend.user.controller;

import com.example.music.backend.verification.OnRegistrationCompleteEvent;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.dto.UserDto;
import com.example.music.backend.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private ApplicationEventPublisher eventPublisher;
    private final UserService service;
    private final PasswordEncoder encoder;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/registration")
    public ResponseEntity<URI> create(@RequestBody UserDto userDto,
                                      HttpServletRequest request) {
        String encodedPassword = encoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        User saved = service.create(userDto);
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(saved, request.getLocale(), appUrl));
        return ResponseEntity.created(URI.create("/users" + saved.getId())).build();
    }
}

