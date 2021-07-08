package com.example.music.backend.user.service;

import com.example.music.backend.common.exception.NotFoundException;
import com.example.music.backend.verification.domain.VerificationToken;
import com.example.music.backend.verification.repository.VerificationTokenRepository;
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

    public UserServiceV1(UserRepository userRepository, UserDtoConverter converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email).stream().count() >= 1;
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email).
                orElseThrow(() -> new NotFoundException("User with email='" + email + "' not found"));
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
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

}
