package com.example.reddit.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

@Service
@NoArgsConstructor
public class JWTUtil {

    public String generateToken(Authentication authentication, String secretKey, Integer expirationDay) {
        User principal = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(new Date())
                .signWith(hmacShaKeyFor(secretKey.getBytes()))
                .setExpiration(calculateExpirationDate(expirationDay))
                .compact();
    }

    private Date calculateExpirationDate(Integer expirationDay) {
        Instant expirationTime = LocalDate.now()
                .plusDays(expirationDay)
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return Date.from(expirationTime);
    }

    public static String extractUsername(String jwt, String secretKey) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        return claims.getSubject();
    }
}
