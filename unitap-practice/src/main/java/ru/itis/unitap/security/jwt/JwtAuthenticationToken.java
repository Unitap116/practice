package ru.itis.unitap.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import ru.itis.unitap.security.details.UserDetailsImpl;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String token;
    private UserDetailsImpl userDetails;

    public JwtAuthenticationToken(String token) {
        super(null);
        this.token = token;
    }

    public JwtAuthenticationToken(UserDetailsImpl userDetails) {
        super(null);
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
