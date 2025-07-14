package ru.unitap.profile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.unitap.profile.dto.ProfileResponseDto;
import ru.unitap.profile.service.ProfileService;

import java.util.UUID;

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
  public ProfileResponseDto getUser(@PathVariable("id") UUID id){
    return profileService.getById(id);
  }
}
