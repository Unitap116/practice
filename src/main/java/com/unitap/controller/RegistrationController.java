package com.unitap.controller;

import com.unitap.dto.RegisterForm;
import com.unitap.entity.User;
import com.unitap.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/register")
@Tag(name = "Регистрация", description = "Создание нового пользователя")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Operation(
            summary = "Регистрация пользователя",
            description = "Регистрирует нового пользователя по email и паролю"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные формы")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> register(@Valid @RequestBody RegisterForm form) {
        return registrationService.register(form);
    }
}

