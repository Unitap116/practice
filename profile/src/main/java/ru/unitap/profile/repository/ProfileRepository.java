package ru.unitap.profile.repository;

import ru.unitap.profile.entity.ProfileEntity;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository {

  Optional<ProfileEntity> getById(UUID id);
}
