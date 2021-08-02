package com.example.music.backend.verification.service;

import com.example.music.backend.user.domain.User;
import com.example.music.backend.verification.domain.VerificationToken;
import com.example.music.backend.verification.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository tokenRepository;

    @Override
    public VerificationToken createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        return tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
}
