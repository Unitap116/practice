package ru.unitap.profile.dto.request;

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
