package com.unitap.security.jwt.service;

import com.unitap.dto.JwtRefreshTokenDto;
import com.unitap.entity.JwtRefreshToken;
import com.unitap.repository.JwtRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtRefreshTokenService {

    private final JwtRefreshTokenRepository tokenRepository;

    public void save(String email, JwtRefreshTokenDto dto) {
        JwtRefreshToken entity = JwtRefreshToken.builder()
                .token(dto.getToken())
                .email(email)
                .expiresAt(dto.getExpiresAt())
                .build();

        tokenRepository.save(entity).subscribe();
    }

    public boolean isValid(String token) {
        return Boolean.TRUE.equals(tokenRepository.findByToken(token)
                .map(t -> t.getExpiresAt().isAfter(java.time.Instant.now()))
                .defaultIfEmpty(false)
                .block());
    }

    public void delete(String token) {
        tokenRepository.deleteById(token).subscribe();
    }
}