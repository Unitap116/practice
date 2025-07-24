package com.unitap.security.jwt.provider;

import com.unitap.security.jwt.RefreshAuthenticationToken;
import com.unitap.security.jwt.service.JwtGenerationService;
import com.unitap.security.jwt.service.JwtRefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RefreshAuthenticationProvider implements AuthenticationProvider {

    private final JwtGenerationService jwtService;
    private final JwtRefreshTokenService refreshTokenService;
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String rawToken = (String) authentication.getCredentials();

        if (!refreshTokenService.isValid(rawToken)) {
            throw new BadCredentialsException("Invalid or expired refresh token");
        }


        String username = jwtService.getUsername(rawToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        refreshTokenService.delete(rawToken);

        return new RefreshAuthenticationToken(userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RefreshAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
