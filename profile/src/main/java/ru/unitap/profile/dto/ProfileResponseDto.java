package ru.unitap.profile.dto;

import java.util.UUID;

public record ProfileResponseDto(
  UUID id,
  String fullName,
  String nickName,
  String avatarUrl,
  String phone,
  String email,
  String company,
  String position,
  String description,
  String portfolio
) {
}
