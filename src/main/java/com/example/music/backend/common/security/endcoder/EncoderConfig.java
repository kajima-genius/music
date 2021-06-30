package com.example.music.backend.common.security.endcoder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EncoderConfig {
    private static final int MIN_LOG = 4;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(MIN_LOG);
    }
}
