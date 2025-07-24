package com.unitap.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.unitap.security.jwt.filter.LoginAuthenticationFilter;
import com.unitap.security.jwt.filter.RefreshAuthenticationFilter;
import com.unitap.security.jwt.filter.TokenAuthenticationFilter;
import com.unitap.security.jwt.matcher.ProcessingRequestMatcher;
import com.unitap.security.jwt.service.JwtGenerationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain
            (HttpSecurity http,
             LoginAuthenticationFilter loginAuthenticationFilter,
             TokenAuthenticationFilter tokenAuthenticationFilter,
             RefreshAuthenticationFilter refreshAuthenticationFilter) throws Exception {

        HttpSecurity httpSecurity = http
                .securityMatcher("/**")
                .addFilterAt(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(refreshAuthenticationFilter, TokenAuthenticationFilter.class)
                .addFilterAt(loginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/register").anonymous()
                        .anyRequest().authenticated())
                .sessionManagement(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter(
            AuthenticationManager authenticationManager,
            @Qualifier("tokenAuthenticationSuccessHandler") AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler) {
        return new LoginAuthenticationFilter(
                "/api/v1/login", authenticationManager, successHandler, failureHandler);
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter(
            JwtGenerationService jwtService,
            AuthenticationManager authenticationManager,
            AuthenticationFailureHandler failureHandler) {

        ProcessingRequestMatcher requestMatcher =
                new ProcessingRequestMatcher("/api/v1/login",
                        "/api/v1/refresh",
                        "/api/v1/register",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/favicon.ico");

        return new TokenAuthenticationFilter(
                requestMatcher, jwtService, authenticationManager, failureHandler);
    }


    @Bean
    public RefreshAuthenticationFilter refreshAuthenticationFilter(
            AuthenticationManager authenticationManager,
            JwtGenerationService jwtService,
            @Qualifier("tokenAuthenticationSuccessHandler") AuthenticationSuccessHandler refreshSuccessHandler,
            AuthenticationFailureHandler failureHandler) {
        return new RefreshAuthenticationFilter(
                "/api/v1/refresh", authenticationManager, jwtService, refreshSuccessHandler, failureHandler);
    }


    @Bean
    public AuthenticationManager providerManager(List<AuthenticationProvider> providers) {
        return new ProviderManager(providers);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) {
        var provider = new DaoAuthenticationProvider(passwordEncoder);

        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Algorithm algorithm(@Value("${jwt.secret}") String secret) {
        return Algorithm.HMAC256(secret);
    }

    @Bean
    public JWTVerifier jwtVerifier(Algorithm algorithm) {
        return JWT.require(algorithm).build();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

}
