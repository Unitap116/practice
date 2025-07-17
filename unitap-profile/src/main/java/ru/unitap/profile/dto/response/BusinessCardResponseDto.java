package ru.unitap.profile.dto.response;

import java.time.Instant;
import java.util.UUID;

public record BusinessCardResponseDto(
  UUID userId,
  UUID templateId,
  Instant data
) {
}
