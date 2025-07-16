package ru.unitap.profile.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.unitap.profile.dto.request.AvatarUploadRequestDto;
import ru.unitap.profile.dto.request.ProfileUpdateRequestDto;
import ru.unitap.profile.dto.response.ProfileResponseDto;
import ru.unitap.profile.entity.ProfileEntity;
import ru.unitap.profile.exception.model.ProfileNotFoundException;
import ru.unitap.profile.mapper.ProfileMapper;
import ru.unitap.profile.repository.ProfileRepository;
import ru.unitap.profile.service.ProfileService;

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
