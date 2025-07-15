package ru.unitap.profile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.unitap.profile.dto.request.AvatarUploadRequestDto;
import ru.unitap.profile.dto.response.ProfileResponseDto;
import ru.unitap.profile.dto.request.ProfileUpdateRequestDto;
import ru.unitap.profile.service.ProfileService;

import java.util.UUID;

@Tag(
  name = "Profile",
  description = "Управление и работа с профилем"
)
@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

  private final ProfileService profileService;

  @Operation(
    summary = "Получение профиля",
    description = "Получает профиль"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Профиль получен"
    )
  })
  @GetMapping("/{id}")
  public ProfileResponseDto getUser(@PathVariable("id") UUID id) {
    return profileService.getById(id);
  }

  @Operation(
    summary = "Обновление профиля",
    description = "Обновляет имя и контакты пользователя"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Профиль обновлен"
    )
  })
  @PatchMapping("/{id}")
  public ProfileResponseDto updateProfile(
    @PathVariable("id") UUID id,
    @RequestBody ProfileUpdateRequestDto updateRequestDto) {
    return profileService.updateProfile(id, updateRequestDto);
  }

  @Operation(
    summary = "Загрузка аватарки",
    description = "Загружает URL аватарки пользователя и сохраняет в Firestore"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Аватарка загружена"
    )
  })
  @PostMapping("/{id}/avatar")
  public ProfileResponseDto uploadAvatar(
    @PathVariable("id") UUID id,
    @RequestBody @Valid AvatarUploadRequestDto avatarUploadRequestDto) {
    return profileService.uploadAvatar(id, avatarUploadRequestDto);
  }
}

