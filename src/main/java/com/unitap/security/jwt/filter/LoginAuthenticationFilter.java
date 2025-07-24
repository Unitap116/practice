package com.unitap.security.jwt.filter;

import com.unitap.dto.EmailPasswordDto;
import com.unitap.security.exception.AuthMethodNotSupportedException;
import com.unitap.utils.JsonUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;


    public LoginAuthenticationFilter(
            String defaultFilterProcessesUrl,
            AuthenticationManager authenticationManager,
            AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler) {
        super(defaultFilterProcessesUrl, authenticationManager);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        if (!POST.asHttpMethod().matches(request.getMethod())) {
            throw new AuthMethodNotSupportedException("Auth method not supported");
        }

        EmailPasswordDto dto = JsonUtil.read(request.getReader(), EmailPasswordDto.class);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

        failureHandler.onAuthenticationFailure(request, response, failed);
    }

}
