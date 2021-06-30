package com.example.music.backend.user.service;

import com.example.music.backend.verification.VerificationToken;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.dto.UserDto;

public interface UserService {

    User getCurrentUser();

    User getUser(Long id);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    User create(UserDto dto);

    UserDto getDto();

}
