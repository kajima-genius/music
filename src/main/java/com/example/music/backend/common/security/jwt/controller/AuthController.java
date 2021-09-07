package com.example.music.backend.common.security.jwt.controller;

import com.example.music.backend.common.security.CustomUserPrincipal;
import com.example.music.backend.common.security.jwt.JwtUtils;
import com.example.music.backend.common.security.jwt.payload.request.LoginRequest;
import com.example.music.backend.common.security.jwt.payload.response.JwtResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()));

        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomUserPrincipal userDetails = (CustomUserPrincipal) authentication.getPrincipal();
        JwtResponse jwtResponse = new JwtResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getRole(),
                jwt);

        return ResponseEntity.ok()
                .header("token", jwt)
                .header("tokenType", jwtResponse.getTokenType())
                .body(jwtResponse);
    }
}
