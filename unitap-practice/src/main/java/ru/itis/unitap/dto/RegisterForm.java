package ru.itis.unitap.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterForm {
    @Email
    @NotBlank
    @Schema(example = "user@example.com")
    private String email;

    @Size(min = 8, message = "Минимум 8 символов")
    @Schema(example = "super-duper-password")
    private String password;
}
