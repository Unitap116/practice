package com.unitap.security.jwt;

import com.unitap.security.details.UserDetailsImpl;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String token;
    private UserDetailsImpl userDetails;

    public JwtAuthenticationToken(String token) {
        super(null);
        this.token = token;
    }

    public JwtAuthenticationToken(UserDetailsImpl userDetails) {
        super(userDetails.getAuthorities());
        this.userDetails = userDetails;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

}
