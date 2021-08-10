package com.example.music.backend.user.controller;

import com.example.music.backend.verification.OnRegistrationCompleteEvent;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.dto.UserDto;
import com.example.music.backend.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private ApplicationEventPublisher eventPublisher;
    private final UserService service;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/registration")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto,
                                          HttpServletRequest request) {
        User saved = service.create(userDto);
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(saved, request.getLocale(), appUrl));
        return ResponseEntity.ok(service.getDto(userDto.getEmail()));
    }
}

