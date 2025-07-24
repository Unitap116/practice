package com.unitap.security.jwt.handler;

import com.unitap.dictionary.UserRole;
import com.unitap.dto.JwtRefreshTokenDto;
import com.unitap.dto.JwtTokenPairDto;
import com.unitap.security.details.UserDetailsImpl;
import com.unitap.security.jwt.service.JwtGenerationService;
import com.unitap.security.jwt.service.JwtRefreshTokenService;
import com.unitap.utils.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtGenerationService jwtService;
    private final JwtRefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String username = userDetails.getUsername();
        UserRole role = userDetails.getUser().getRole();

        JwtTokenPairDto tokenPair = jwtService.getTokenPair(username, role);

        JwtRefreshTokenDto refreshDto = new JwtRefreshTokenDto()
                .setToken(tokenPair.getRefreshToken())
                .setExpiresAt(jwtService.getExpiration(tokenPair.getRefreshToken()));

        refreshTokenService.save(username, refreshDto);

        response.setStatus(200);
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.getWriter().write(JsonUtil.write(tokenPair));
    }

}
