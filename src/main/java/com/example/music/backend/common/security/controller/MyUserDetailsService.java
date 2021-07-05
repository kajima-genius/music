package com.example.music.backend.common.security.controller;

import com.example.music.backend.common.security.CustomUserPrincipal;
import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public MyUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User with email='" + email + "' not found"));
        return new CustomUserPrincipal(user);
    }
}
