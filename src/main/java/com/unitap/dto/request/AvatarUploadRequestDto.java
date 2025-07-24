package com.unitap.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AvatarUploadRequestDto(

  @NotBlank(message = "validation.not-blank")
  String avatarUrl
) {
}
