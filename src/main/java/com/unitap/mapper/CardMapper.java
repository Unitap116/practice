package com.unitap.mapper;

import com.unitap.dto.request.CardRequest;
import com.unitap.dto.response.CardResponse;
import com.unitap.entity.Card;
import org.mapstruct.Mapper;

@Mapper
public interface CardMapper {

    Card toEntity(CardRequest cardRequest);
    CardResponse toResponse(Card card);

}
