package com.unitap.repository;



import com.unitap.entity.ProfileEntity;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository {

  Optional<ProfileEntity> getById(UUID id);

    ProfileEntity save(ProfileEntity profileEntity);
}
