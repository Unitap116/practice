package ru.itis.unitap.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.itis.unitap.dto.RegisterForm;
import ru.itis.unitap.entity.User;
import ru.itis.unitap.service.RegistrationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> register(@Valid @RequestBody RegisterForm form) {
        return registrationService.register(form);
    }
}

