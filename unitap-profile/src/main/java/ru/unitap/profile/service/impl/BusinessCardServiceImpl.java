package ru.unitap.profile.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.unitap.profile.dto.response.BusinessCardResponseDto;
import ru.unitap.profile.entity.BusinessCardEntity;
import ru.unitap.profile.exception.model.TemplateNotFoundException;
import ru.unitap.profile.mapper.BusinessCardMapper;
import ru.unitap.profile.repository.BusinessCardRepository;
import ru.unitap.profile.repository.TemplateRepository;
import ru.unitap.profile.service.BusinessCardService;

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
