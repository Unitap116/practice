package ru.unitap.profile.service;

import ru.unitap.profile.dto.request.AvatarUploadRequestDto;
import ru.unitap.profile.dto.request.ProfileUpdateRequestDto;
import ru.unitap.profile.dto.response.ProfileResponseDto;

import java.util.UUID;

public interface ProfileService {

  ProfileResponseDto getById(UUID id);

  ProfileResponseDto updateProfile(UUID id, ProfileUpdateRequestDto dto);

  ProfileResponseDto uploadAvatar(UUID id, AvatarUploadRequestDto dto);
}
