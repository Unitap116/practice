package ru.itis.unitap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import ru.itis.unitap.dictionary.UserRole;
import ru.itis.unitap.dto.RegisterForm;
import ru.itis.unitap.entity.User;
import ru.itis.unitap.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<User> register(RegisterForm form) {
        return userRepository.findByEmail(form.getEmail())
                .flatMap(u -> Mono.<User>error(
                        new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists")))
                .switchIfEmpty(Mono.defer(() -> {
                    User user = User.builder()
                            .id(UUID.randomUUID().toString())
                            .email(form.getEmail())
                            .password(passwordEncoder.encode(form.getPassword()))
                            .role(UserRole.USER)
                            .build();
                    return userRepository.save(user);
                }));
    }

}
