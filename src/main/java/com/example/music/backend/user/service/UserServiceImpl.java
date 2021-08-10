package com.example.music.backend.user.service;

import com.example.music.backend.common.exception.NotFoundException;
import com.example.music.backend.user.converter.UserDtoMapper;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.dto.UserDto;
import com.example.music.backend.user.exception.UserAlreadyExistException;
import com.example.music.backend.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public User create(UserDto dto) {
        if (emailExist(dto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + dto.getEmail());
        }

        String encodedPassword = encoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);
        User newUser = UserDtoMapper.INSTANCE.toEntity(dto);
        return userRepository.save(newUser);
    }

    @Override
    public void processOAuthPostLogin(OAuth2User oAuth2User) {
        if (!emailExist(oAuth2User.getAttribute("email"))) {
            User newUser = new User();
            newUser.setEmail(oAuth2User.getAttribute("email"));
            newUser.setEnabled(true);
            userRepository.save(newUser);
        }
    }

    @Override
    public UserDto getDto(String email) {
        User entity = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email" + email + "not found"));
        return UserDtoMapper.INSTANCE.toDto(entity);
    }
}
