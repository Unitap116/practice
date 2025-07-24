package com.unitap.service.impl;

import com.unitap.dto.request.AvatarUploadRequestDto;
import com.unitap.dto.request.ProfileUpdateRequestDto;
import com.unitap.dto.response.ProfileResponseDto;
import com.unitap.entity.ProfileEntity;
import com.unitap.mapper.ProfileMapper;
import com.unitap.repository.ProfileRepository;
import com.unitap.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

  private final ProfileRepository profileRepository;

  private final ProfileMapper profileMapper;

  @Override
  public ProfileResponseDto getById(UUID id) {
    ProfileEntity profileEntity = profileRepository.getById(id)
      .orElseThrow(ProfileNotFoundException::new);
    return profileMapper.toResponse(profileEntity);
  }

  @Override
  public ProfileResponseDto updateProfile(UUID id, ProfileUpdateRequestDto dto) {
    ProfileEntity profileEntity = profileRepository.getById(id)
      .orElseThrow(ProfileNotFoundException::new);

    if (dto.fullName() != null) profileEntity.setFullName(dto.fullName());
    if (dto.phoneNumber() != null) profileEntity.setPhone(dto.phoneNumber());
    if (dto.email() != null) profileEntity.setEmail(dto.email());
    if (dto.company() != null) profileEntity.setCompany(dto.company());
    if (dto.position() != null) profileEntity.setPosition(dto.position());
    if (dto.description() != null) profileEntity.setDescription(dto.description());
    if (dto.portfolio() != null) profileEntity.setPortfolio(dto.portfolio());

    ProfileEntity updated = profileRepository.save(profileEntity);

    return profileMapper.toResponse(updated);
  }

  @Override
  public ProfileResponseDto uploadAvatar(UUID id, AvatarUploadRequestDto dto) {
    ProfileEntity profileEntity = profileRepository.getById(id)
      .orElseThrow(ProfileNotFoundException::new);

    profileEntity.setAvatarUrl(dto.avatarUrl());

    ProfileEntity updated = profileRepository.save(profileEntity);

    return profileMapper.toResponse(updated);
  }
}
