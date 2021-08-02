package com.example.music.backend.user.controller;

import com.example.music.backend.user.repository.UserRepository;
import com.example.music.backend.verification.OnRegistrationCompleteEvent;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.dto.UserDto;
import com.example.music.backend.user.exception.UserAlreadyExistException;
import com.example.music.backend.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
public class UserController {

    private ApplicationEventPublisher eventPublisher;
    private final UserService service;
    private final PasswordEncoder encoder;
    private final UserRepository repository;

    @PostMapping(produces = APPLICATION_JSON_VALUE, path = "/user/registration")
    public ModelAndView create(@ModelAttribute("UserDto") UserDto userDto,
                               HttpServletRequest request, Errors errors) {
        ModelAndView mav = new ModelAndView("successRegister", "user", userDto);
        try {
            String encodedPassword = encoder.encode(userDto.getPassword());
            userDto.setPassword(encodedPassword);
            User saved = service.create(userDto);
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(saved, request.getLocale(), appUrl));
        } catch (UserAlreadyExistException uaeEx) {
            mav.setViewName("registration");
            mav.addObject("message", "An account for that username/email already exists.");
        } catch (RuntimeException ex) {
            mav.setViewName("emailError");
        }
        return mav;
    }

    @GetMapping(path = "/users")
    public List<User> getClients() {
        return repository.findAll();
    }

    @GetMapping(path = "/user/registration")
    public String getPage(Model model) {
        model.addAttribute("UserDto", new UserDto());
        return "registration";
    }

    @GetMapping(path = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping(path = "/home")
    public String getHomePage() {
        return "home";
    }
}
