package ru.unitap.profile.service;

import ru.unitap.profile.dto.response.BusinessCardResponseDto;

import java.util.UUID;

public interface BusinessCardService {
  BusinessCardResponseDto chooseBusinessCardTemplate(UUID userId, UUID templateId);
}
