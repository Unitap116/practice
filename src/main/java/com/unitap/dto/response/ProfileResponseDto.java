package com.unitap.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
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
