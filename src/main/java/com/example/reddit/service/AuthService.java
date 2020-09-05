package com.example.reddit.service;

import com.example.reddit.DTO.*;
import com.example.reddit.entity.User;
import com.example.reddit.entity.VerificationToken;
import com.example.reddit.repository.UserRepository;
import com.example.reddit.repository.VerificationTokenRepository;
import com.example.reddit.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${security.jwt.secretKey}")
    private String secretKey;

    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public void signup(RegisterRequestDTO registerRequestDTO) {
        User user = new User();
        user.setEmail(registerRequestDTO.getEmail());
        user.setEnabled(false);
        user.setUsername(registerRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        userRepository.save(user);

        String verificationToken = generateVerificationToken(user);
        String text = "Dear " + user.getUsername() + ",\nThank you for signing up to our website. Please click on the below link to activate your account : "
                + "http://localhost:8080/api/auth/accountVerification/" + verificationToken;
        emailService.sendMail( new AuthenticationMail("Activation process...",text, user.getEmail()));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);

        return token;

    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> optionalToken = verificationTokenRepository.findByToken(token);
        if( optionalToken.isPresent()) {
            enableUser(optionalToken.get());
        }
        else
            throw new EntityNotFoundException();
    }

    @Transactional
    private void enableUser(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if( optionalUser.isPresent() ) {
            User user = optionalUser.get();
            user.setEnabled(true);
            userRepository.save(user);
        }
    }

    public AuthenticationResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),
                loginRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtUtil.generateToken(authenticate, secretKey, 7);

        return AuthenticationResponseDTO.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expirationDate(jwtUtil.calculateExpirationDate(7).toInstant())
                .username(loginRequestDTO.getUsername())
                .build();
    }

    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public AuthenticationResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtUtil.generateTokenWithUsername(refreshTokenRequest.getUsername(), secretKey, 7);

        return AuthenticationResponseDTO.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expirationDate(jwtUtil.calculateExpirationDate(7).toInstant())
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public void logout( RefreshTokenRequestDTO refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
    }
}
