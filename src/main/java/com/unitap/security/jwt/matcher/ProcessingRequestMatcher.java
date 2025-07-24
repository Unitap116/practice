package com.unitap.security.jwt.matcher;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import java.util.List;

@RequiredArgsConstructor
public class ProcessingRequestMatcher implements RequestMatcher {
    private final List<String> pathsToSkip;
    private final AntPathMatcher matcher = new AntPathMatcher();

    public ProcessingRequestMatcher(String... pathsToSkip) {
        this.pathsToSkip = List.of(pathsToSkip);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        String path = request.getServletPath();
        return pathsToSkip.stream().noneMatch(pattern -> matcher.match(pattern, path));
    }
}

