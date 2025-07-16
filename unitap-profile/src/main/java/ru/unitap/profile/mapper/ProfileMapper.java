package ru.unitap.profile.mapper;

import org.mapstruct.Mapper;
import ru.unitap.profile.dto.response.ProfileResponseDto;
import ru.unitap.profile.entity.ProfileEntity;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

  ProfileResponseDto toResponse(ProfileEntity profileEntity);
}
