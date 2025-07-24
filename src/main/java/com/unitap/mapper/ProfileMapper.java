package com.unitap.mapper;

import com.unitap.dto.response.ProfileResponseDto;
import com.unitap.entity.ProfileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

  ProfileResponseDto toResponse(ProfileEntity profileEntity);
}
