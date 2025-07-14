package ru.unitap.profile.service;

import ru.unitap.profile.dto.ProfileResponseDto;

import java.util.UUID;

public interface ProfileService {

  ProfileResponseDto getById(UUID id);
}
