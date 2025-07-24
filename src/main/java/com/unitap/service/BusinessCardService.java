package com.unitap.service;

import com.unitap.dto.response.BusinessCardResponseDto;

import java.util.UUID;

public interface BusinessCardService {
  BusinessCardResponseDto chooseBusinessCardTemplate(UUID userId, UUID templateId);
}
