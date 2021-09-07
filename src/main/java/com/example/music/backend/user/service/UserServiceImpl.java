package com.example.music.backend.user.service;

import com.example.music.backend.common.exception.NotFoundException;
import com.example.music.backend.user.converter.UserDtoMapper;
import com.example.music.backend.user.converter.UserResponseMapper;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.dto.UserDto;
import com.example.music.backend.user.exception.UserAlreadyExistException;
import com.example.music.backend.user.repository.UserRepository;
import com.example.music.backend.user.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public UserResponse create(UserDto dto) {
        if (emailExist(dto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + dto.getEmail());
        }

        String encodedPassword = encoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);
        User newUser = UserDtoMapper.INSTANCE.toEntity(dto);
        return UserResponseMapper.INSTANCE.toResponse(userRepository.save(newUser));
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User entity = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email" + email + "not found"));
        UserResponse response = UserResponseMapper.INSTANCE.toResponse(entity);
        return response;
    }
}
