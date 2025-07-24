package com.unitap.service;

import com.unitap.dto.request.AvatarUploadRequestDto;
import com.unitap.dto.request.ProfileUpdateRequestDto;
import com.unitap.dto.response.ProfileResponseDto;
import com.unitap.entity.ProfileEntity;
import com.unitap.exception.profile.ProfileNotFoundException;
import com.unitap.mapper.ProfileMapper;
import com.unitap.repository.ProfileRepository;
import com.unitap.service.impl.ProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;


import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ProfileServiceImplTest {

  @InjectMocks
  private ProfileServiceImpl service;

  @Mock
  private ProfileRepository repository;

  @Mock
  private ProfileMapper mapper;

  private UUID id;
  private ProfileEntity entity;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new ProfileEntity();
    entity.setId(id.toString());
  }

  @Test
  void getByIdSuccess() {
    when(repository.getById(id)).thenReturn(Optional.of(entity));
    ProfileResponseDto dto = mock(ProfileResponseDto.class);
    when(mapper.toResponse(entity)).thenReturn(dto);

    ProfileResponseDto result = service.getById(id);

    assertEquals(dto, result);
    verify(repository).getById(id);
    verify(mapper).toResponse(entity);
  }

  @Test
  void getByIdNotFound() {
    when(repository.getById(id)).thenReturn(Optional.empty());

    assertThrows(ProfileNotFoundException.class, () -> service.getById(id));

    verify(repository).getById(id);
    verifyNoInteractions(mapper);
  }

  @Test
  void updateProfileSuccess() {
    ProfileUpdateRequestDto dto = new ProfileUpdateRequestDto(
      "John Doe", "1234567890", "john@example.com", "Company", "Developer", "Desc", "Portfolio"
    );
    when(repository.getById(id)).thenReturn(Optional.of(entity));
    when(repository.save(entity)).thenReturn(entity);
    ProfileResponseDto responseDto = mock(ProfileResponseDto.class);
    when(mapper.toResponse(entity)).thenReturn(responseDto);

    ProfileResponseDto result = service.updateProfile(id, dto);

    assertEquals(dto.fullName(), entity.getFullName());
    assertEquals(dto.phoneNumber(), entity.getPhone());
    assertEquals(dto.email(), entity.getEmail());
    assertEquals(dto.company(), entity.getCompany());
    assertEquals(dto.position(), entity.getPosition());
    assertEquals(dto.description(), entity.getDescription());
    assertEquals(dto.portfolio(), entity.getPortfolio());

    assertEquals(responseDto, result);

    verify(repository).getById(id);
    verify(repository).save(entity);
    verify(mapper).toResponse(entity);
  }

  @Test
  void uploadAvatarSuccess() {
    AvatarUploadRequestDto dto = new AvatarUploadRequestDto("http://avatar.url");
    when(repository.getById(id)).thenReturn(Optional.of(entity));
    when(repository.save(entity)).thenReturn(entity);
    ProfileResponseDto responseDto = mock(ProfileResponseDto.class);
    when(mapper.toResponse(entity)).thenReturn(responseDto);

    ProfileResponseDto result = service.uploadAvatar(id, dto);

    assertEquals(dto.avatarUrl(), entity.getAvatarUrl());
    assertEquals(responseDto, result);

    verify(repository).getById(id);
    verify(repository).save(entity);
    verify(mapper).toResponse(entity);
  }

  @Test
  void uploadAvatarNotFound() {
    when(repository.getById(id)).thenReturn(Optional.empty());

    assertThrows(ProfileNotFoundException.class, () -> service.uploadAvatar(id, new AvatarUploadRequestDto("url")));

    verify(repository).getById(id);
    verifyNoInteractions(mapper);
  }
}
