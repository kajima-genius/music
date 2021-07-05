package com.example.music.backend.verification.service;

import com.example.music.backend.user.domain.User;
import com.example.music.backend.verification.domain.VerificationToken;

public interface VerificationTokenService {

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

}
