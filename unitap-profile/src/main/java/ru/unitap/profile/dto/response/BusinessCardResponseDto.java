package ru.unitap.profile.dto.response;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record BusinessCardResponseDto(
  UUID userId,
  UUID templateId,
  Instant data
) {
}
