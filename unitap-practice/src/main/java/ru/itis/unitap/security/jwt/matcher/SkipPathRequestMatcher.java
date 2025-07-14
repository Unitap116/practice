package ru.itis.unitap.security.jwt.matcher;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

@RequiredArgsConstructor
public class SkipPathRequestMatcher implements RequestMatcher {

    private final List<String> pathsToSkip;

    public SkipPathRequestMatcher(String... pathsToSkip) {
        this.pathsToSkip = List.of(pathsToSkip);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        return pathsToSkip.stream().noneMatch(servletPath::equals);
    }
}
