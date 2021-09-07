package com.example.music.backend.common.security.jwt.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

    private String tokenType = "Bearer";
    private Long id;
    private String email;
    private String role;
    private String token;

    public JwtResponse(Long id, String email, String role, String token) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.token = token;
    }
}
