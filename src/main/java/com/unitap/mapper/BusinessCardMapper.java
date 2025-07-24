package com.unitap.mapper;

import org.mapstruct.Mapper;
import ru.unitap.profile.dto.response.BusinessCardResponseDto;
import ru.unitap.profile.entity.BusinessCardEntity;

@Mapper(componentModel = "spring")
public interface BusinessCardMapper {

  BusinessCardResponseDto toResponse(BusinessCardEntity entity);
}
