package com.unitap.security.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.unitap.dictionary.UserRole;
import com.unitap.dto.JwtTokenPairDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class JwtGenerationService {

    private final static String AUTH_HEADER = "Authorization";
    private final static String AUTH_HEADER_PREFIX = "Bearer ";

    private static final String USERNAME_CLAIM = "username";
    private static final String TYPE_CLAIM = "type";
    private static final String ROLE_CLAIM = "role";

    @Value("${jwt.access-token.ttl}")
    private Long accessTokenTtl;
    @Value("${jwt.refresh-token.ttl}")
    private Long refreshTokenTtl;

    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;

    public JwtTokenPairDto getTokenPair(String username, UserRole role) {
        return new JwtTokenPairDto(
                createAccessToken(username, role),
                createRefreshToken(username));
    }

    private String createAccessToken(String username, UserRole role) {
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenTtl))
                .withClaim(USERNAME_CLAIM, username)
                .withClaim(TYPE_CLAIM, "access")
                .withClaim(ROLE_CLAIM, role.name())
                .sign(algorithm);
    }

    private String createRefreshToken(String username) {
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenTtl))
                .withClaim(USERNAME_CLAIM, username)
                .withClaim(TYPE_CLAIM, "refresh")
                .sign(algorithm);
    }

    public String getRawToken(HttpServletRequest request) {
        String header = request.getHeader(AUTH_HEADER);

        if (header != null && header.startsWith(AUTH_HEADER_PREFIX)) {
            return header.substring(AUTH_HEADER_PREFIX.length());
        }

        throw new BadCredentialsException("Token not found");
    }

    public String getUsername(String token) {
        return Optional.of(jwtVerifier.verify(token))
                .map(jwt -> jwt.getClaim(USERNAME_CLAIM))
                .map(Claim::asString)
                .orElse(null);
    }

    public Instant getExpiration(String token) {
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getExpiresAt().toInstant();
    }
    public String getUsernameIfAccessToken(String token) {
        DecodedJWT jwt = jwtVerifier.verify(token);

        String type = jwt.getClaim("type").asString();
        if (!"access".equals(type)) {
            throw new BadCredentialsException("Token is not an access token");
        }

        return jwt.getClaim("username").asString();
    }

}
