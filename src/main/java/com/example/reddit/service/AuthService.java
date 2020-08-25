package com.example.reddit.service;

import com.example.reddit.DTO.AuthenticationMail;
import com.example.reddit.DTO.RegisterRequestDTO;
import com.example.reddit.entity.User;
import com.example.reddit.entity.VerificationToken;
import com.example.reddit.repository.UserRepository;
import com.example.reddit.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

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
}
