package com.unitap.security.jwt.provider;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.unitap.security.details.UserDetailsImpl;
import com.unitap.security.details.UserDetailsServiceImpl;
import com.unitap.security.jwt.JwtAuthenticationToken;
import com.unitap.security.jwt.service.JwtGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final JwtGenerationService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String rawToken = (String) authentication.getCredentials();

        String username = getUsername(rawToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

        return new JwtAuthenticationToken(userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private String getUsername(String rawToken) {
        try {
            return jwtService.getUsernameIfAccessToken(rawToken);
        } catch (JWTVerificationException e) {
            throw new BadCredentialsException("Invalid token");
        }
    }

}
