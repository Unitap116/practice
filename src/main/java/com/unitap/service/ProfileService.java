package com.unitap.service;

import com.unitap.dto.request.ProfileUpdateRequestDto;
import com.unitap.dto.request.AvatarUploadRequestDto;
import com.unitap.dto.response.ProfileResponseDto;


import java.util.UUID;

public interface ProfileService {

  ProfileResponseDto getById(UUID id);

  ProfileResponseDto updateProfile(UUID id, ProfileUpdateRequestDto dto);

  ProfileResponseDto uploadAvatar(UUID id, AvatarUploadRequestDto dto);
}
