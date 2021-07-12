package com.example.music.backend.user.service;

import com.example.music.backend.verification.domain.VerificationToken;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.dto.UserDto;

public interface UserService {

    User getCurrentUser();

    User getUser(String email);

    void saveRegisteredUser(User user);

    User create(UserDto dto);

    void processOAuthPostLogin(String username);

}
