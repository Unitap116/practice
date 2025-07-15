package ru.itis.unitap.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.itis.unitap.dto.UserDto;
import ru.itis.unitap.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@Tag(name = "Admin - Users", description = "Администрирование пользователей")
public class AdminUserController {

    private final UserService userService;

    @Operation(
            summary = "Получить список всех пользователей",
            description = "Только для админов. Возвращает список зарегистрированных пользователей."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получен список пользователей"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещён (не админ)")
    })
    @GetMapping("/users")
    public Flux<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    @Operation(
            summary = "Удалить пользователя по ID",
            description = "Удаляет пользователя с указанным идентификатором. Только для админов."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь успешно удалён"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещён")
    })
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUser(
            @Parameter(description = "ID пользователя", required = true)
            @PathVariable String id) {
        return userService.deleteUserById(id);
    }

}
