package com.unitap.mapper;

import com.unitap.dto.response.BusinessCardResponseDto;
import com.unitap.entity.BusinessCardEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusinessCardMapper {

  BusinessCardResponseDto toResponse(BusinessCardEntity entity);
}
