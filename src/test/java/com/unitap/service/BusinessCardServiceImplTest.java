package com.unitap.service;

import com.unitap.dto.response.BusinessCardResponseDto;
import com.unitap.entity.BusinessCardEntity;
import com.unitap.exception.profile.TemplateNotFoundException;
import com.unitap.mapper.BusinessCardMapper;
import com.unitap.repository.BusinessCardRepository;
import com.unitap.repository.TemplateRepository;
import com.unitap.service.impl.BusinessCardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class BusinessCardServiceImplTest {

  @InjectMocks
  private BusinessCardServiceImpl service;

  @Mock
  private BusinessCardRepository businessCardRepository;

  @Mock
  private TemplateRepository templateRepository;

  @Mock
  private BusinessCardMapper businessCardMapper;

  private UUID userId;
  private UUID templateId;

  @BeforeEach
  void setUp() {
    userId = UUID.randomUUID();
    templateId = UUID.randomUUID();
  }

  @Test
  void chooseBusinessCardTemplateSuccess() {
    when(templateRepository.getById(templateId)).thenReturn(Optional.of(new Object()));

    BusinessCardEntity savedEntity = BusinessCardEntity.builder()
      .templateId(templateId.toString())
      .userId(userId.toString())
      .data(Instant.now())
      .build();

    when(businessCardRepository.save(any(BusinessCardEntity.class)))
      .thenReturn(savedEntity);

    BusinessCardResponseDto responseDto = mock(BusinessCardResponseDto.class);
    when(businessCardMapper.toResponse(savedEntity))
      .thenReturn(responseDto);

    BusinessCardResponseDto result = service.chooseBusinessCardTemplate(userId, templateId);

    assertEquals(responseDto, result);

    verify(templateRepository).getById(templateId);
    verify(businessCardRepository).save(any(BusinessCardEntity.class));
    verify(businessCardMapper).toResponse(savedEntity);
  }

  @Test
  void chooseBusinessCardTemplateTemplateNotFound() {
    when(templateRepository.getById(templateId)).thenReturn(Optional.empty());

    assertThrows(TemplateNotFoundException.class,
      () -> service.chooseBusinessCardTemplate(userId, templateId));

    verify(templateRepository).getById(templateId);
    verifyNoInteractions(businessCardRepository, businessCardMapper);
  }
}
