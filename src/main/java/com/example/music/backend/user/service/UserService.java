package com.example.music.backend.user.service;

import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.dto.UserDto;
import com.example.music.backend.user.response.UserResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UserService {

    void saveRegisteredUser(User user);

    UserResponse create(UserDto dto);

    UserResponse getUserByEmail(String email);
}
