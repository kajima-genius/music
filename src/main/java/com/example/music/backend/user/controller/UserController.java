package com.example.music.backend.user.controller;

import com.example.music.backend.verification.OnRegistrationCompleteEvent;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.dto.UserDto;
import com.example.music.backend.user.exception.UserAlreadyExistException;
import com.example.music.backend.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
public class UserController {

    private ApplicationEventPublisher eventPublisher;
    private final UserService service;
    private final PasswordEncoder encoder;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, path = "/user/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody UserDto userDto,
                                             HttpServletRequest request) {
        HttpStatus status;
        try {
            String encodedPassword = encoder.encode(userDto.getPassword());
            userDto.setPassword(encodedPassword);
            User saved = service.create(userDto);
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(saved, request.getLocale(), appUrl));
            status = HttpStatus.OK;
        } catch (UserAlreadyExistException uaeEx) {
            status = HttpStatus.BAD_REQUEST;
        } catch (RuntimeException ex) {
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).build();
    }

//    @GetMapping(path = "/user/registration")
//    public String getPage(Model model) {
//        model.addAttribute("UserDto", new UserDto());
//        return "registration";
//    }
//
//    @GetMapping(path = "/login")
//    public String getLoginPage() {
//        return "login";
//    }
//
//    @GetMapping(path = "/home")
//    public String getHomePage() {
//        return "home";
//    }
}
