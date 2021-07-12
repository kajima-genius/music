package com.example.music.backend.user.controller;

import com.example.music.backend.verification.OnRegistrationCompleteEvent;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.dto.UserDto;
import com.example.music.backend.user.exception.UserAlreadyExistException;
import com.example.music.backend.user.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
public class UserController {

    private ApplicationEventPublisher eventPublisher;
    private final UserService service;

    public UserController(UserService service, ApplicationEventPublisher eventPublisher) {
        this.service = service;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, path = "/user/registration")
    public ModelAndView create(@ModelAttribute("UserDto") UserDto userDto,
                               HttpServletRequest request, Errors errors) {
        ModelAndView mav = new ModelAndView("successRegister", "user", userDto);
        try {
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
