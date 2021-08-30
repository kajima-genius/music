package com.example.music.backend.verification.service;

import com.example.music.backend.user.domain.User;
import com.example.music.backend.verification.domain.VerificationToken;

public interface VerificationTokenService {

    VerificationToken createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

}
