package ru.itis.unitap.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "Информация о зарегистрированном пользователе")
public class UserDto {

    @Schema(description = "Уникальный идентификатор пользователя", example = "123")
    private String id;

    @Schema(description = "Email пользователя", example = "user@example.com")
    private String email;

    @Schema(description = "Роль пользователя", example = "USER")
    private String role;
}
