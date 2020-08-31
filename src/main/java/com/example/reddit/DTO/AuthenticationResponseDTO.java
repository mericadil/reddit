package com.example.reddit.DTO;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {
    private String authenticationToken;
    private String username;
    /*
    private String refreshToken;
    private Instant expirationDate;*/
}
