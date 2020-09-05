package com.example.reddit.repository;

import com.example.reddit.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken( String token);

    @Transactional
    void deleteByToken( String token);
}
