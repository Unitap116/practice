package com.unitap.service.impl;

import com.unitap.dto.response.CardResponse;
import com.unitap.exception.card.CardNotFoundException;
import com.unitap.mapper.CardMapper;
import com.unitap.repository.CardRepository;
import com.unitap.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BaseCardService implements CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public CardResponse getById(UUID cardId) {
        return cardMapper.toResponse(cardRepository
                        .findById(cardId)
                        .orElseThrow(() -> new CardNotFoundException(cardId))
                );
    }

    @Override
    public String getQrById(UUID cardId) {
        return "";
    }


}
