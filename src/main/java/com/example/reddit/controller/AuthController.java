package com.example.reddit.controller;

import com.example.reddit.DTO.AuthenticationResponseDTO;
import com.example.reddit.DTO.LoginRequestDTO;
import com.example.reddit.DTO.RefreshTokenRequestDTO;
import com.example.reddit.DTO.RegisterRequestDTO;
import com.example.reddit.service.AuthService;
import com.example.reddit.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequestDTO registerRequestDTO) {
        authService.signup(registerRequestDTO);
        return new ResponseEntity<>("User registration is successful!", HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account activated successfully!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponseDTO refreshTokens(@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequest) {
        authService.logout(refreshTokenRequest);
        return new ResponseEntity<>("Refresh token deleted successfully!", HttpStatus.OK);
    }
}
