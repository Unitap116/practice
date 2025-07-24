package com.unitap.service.impl;

import com.unitap.dto.response.BusinessCardResponseDto;
import com.unitap.entity.BusinessCardEntity;
import com.unitap.mapper.BusinessCardMapper;
import com.unitap.repository.BusinessCardRepository;
import com.unitap.repository.TemplateRepository;
import com.unitap.service.BusinessCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BusinessCardServiceImpl implements BusinessCardService {

  private final BusinessCardRepository businessCardRepository;
  private final TemplateRepository templateRepository;

  private final BusinessCardMapper businessCardMapper;

  @Override
  public BusinessCardResponseDto chooseBusinessCardTemplate(UUID userId, UUID templateId) {
    templateRepository.getById(templateId).orElseThrow(TemplateNotFoundException::new);
    BusinessCardEntity businessCard = BusinessCardEntity.builder()
      .templateId(templateId.toString())
      .userId(userId.toString())
      .data(Instant.now())
      .build();
    return businessCardMapper.toResponse(
      businessCardRepository.save(businessCard)
    );
  }
}
