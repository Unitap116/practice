package ru.itis.unitap.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.itis.unitap.entity.JwtRefreshToken;

@Repository
public interface JwtRefreshTokenRepository extends FirestoreReactiveRepository<JwtRefreshToken> {
    Mono<JwtRefreshToken> findByToken(String token);
}