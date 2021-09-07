package com.example.music.backend.common.security.service;

import com.example.music.backend.common.exception.NotFoundException;
import com.example.music.backend.common.security.CustomUserPrincipal;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.exception.UserEmailNotVerifiedException;
import com.example.music.backend.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User with email='" + email + "' not found"));
        if (!user.isEnabled()) {
            throw new UserEmailNotVerifiedException("Email not verified");
        }
        return new CustomUserPrincipal(user);
    }
}
