package com.unitap.repository;


import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.unitap.entity.JwtRefreshToken;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface JwtRefreshTokenRepository extends FirestoreReactiveRepository<JwtRefreshToken> {
    Mono<JwtRefreshToken> findByToken(String token);
}