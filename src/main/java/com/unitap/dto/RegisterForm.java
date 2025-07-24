package com.unitap.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Форма регистрации нового пользователя")
public class RegisterForm {

    @Email
    @NotBlank
    @Schema(description = "Email пользователя", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Size(min = 8, message = "Минимум 8 символов")
    @Schema(description = "Пароль пользователя (минимум 8 символов)", example = "super-duper-password", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
