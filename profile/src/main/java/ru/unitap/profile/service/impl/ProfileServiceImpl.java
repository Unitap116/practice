package ru.unitap.profile.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.unitap.profile.dto.ProfileResponseDto;
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
}
