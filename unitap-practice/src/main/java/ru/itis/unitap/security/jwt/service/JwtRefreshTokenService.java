package ru.itis.unitap.security.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.unitap.dto.JwtRefreshTokenDto;
import ru.itis.unitap.entity.JwtRefreshToken;
import ru.itis.unitap.repository.JwtRefreshTokenRepository;

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