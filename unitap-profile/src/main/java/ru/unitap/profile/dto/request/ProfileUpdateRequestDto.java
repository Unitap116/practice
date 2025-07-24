package ru.unitap.profile.dto.request;

import lombok.Builder;

@Builder
public record ProfileUpdateRequestDto(
  String fullName,
  String phoneNumber,
  String email,
  String company,
  String position,
  String description,
  String portfolio
) {
}
