package ru.unitap.profile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.unitap.profile.dto.response.BusinessCardResponseDto;
import ru.unitap.profile.entity.BusinessCardEntity;
import ru.unitap.profile.exception.model.TemplateNotFoundException;
import ru.unitap.profile.mapper.BusinessCardMapper;
import ru.unitap.profile.repository.BusinessCardRepository;
import ru.unitap.profile.repository.TemplateRepository;
import ru.unitap.profile.service.impl.BusinessCardServiceImpl;

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
