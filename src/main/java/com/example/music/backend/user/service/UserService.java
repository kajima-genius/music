package com.example.music.backend.user.service;

import com.example.music.backend.verification.domain.VerificationToken;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.dto.UserDto;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UserService {

    void saveRegisteredUser(User user);

    User create(UserDto dto);

    void processOAuthPostLogin(OAuth2User oAuth2User);
}
