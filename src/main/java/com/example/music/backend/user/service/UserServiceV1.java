package com.example.music.backend.user.service;

import com.example.music.backend.common.exception.NotFoundException;
import com.example.music.backend.verification.VerificationToken;
import com.example.music.backend.verification.VerificationTokenRepository;
import com.example.music.backend.user.converter.UserDtoConverter;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.dto.UserDto;
import com.example.music.backend.user.exception.UserAlreadyExistException;
import com.example.music.backend.user.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceV1 implements UserService {

    private final UserRepository userRepository;
    private final UserDtoConverter converter;
    private final VerificationTokenRepository tokenRepository;

    public UserServiceV1(UserRepository userRepository, UserDtoConverter converter, VerificationTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.converter = converter;
        this.tokenRepository = tokenRepository;
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    } //todo раньше было ==

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findByEmail(SecurityContextHolder
                .getContext().getAuthentication().getName()).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User create(UserDto dto) {
        if (emailExist(dto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + dto.getEmail());
        }

        User newUser = converter.toEntity(dto);
        return userRepository.save(newUser);
    }

    @Override
    public UserDto getDto() {
        return null;
    }
}
