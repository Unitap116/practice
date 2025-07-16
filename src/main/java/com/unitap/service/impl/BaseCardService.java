package com.unitap.service.impl;

import com.unitap.dto.response.CardResponse;
import com.unitap.mapper.CardMapper;
import com.unitap.service.CardService;
import com.unitap.service.QrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BaseCardService implements CardService {

    private final QrService qrService;
    private final CardMapper cardMapper;

    @Override
    public CardResponse getById(UUID cardId) {
        /* TODO add firestore implementation */
        /* return cardMapper.toResponse(cardRepository
                        .findById(cardId)
                        .orElseThrow(() -> new CardNotFoundException(cardId))
                );*/
        return null; /* TODO change null */
    }

    @Override
    public byte[] getQrById(UUID userId) {
        /* TODO implement method */
        String content = "";
        return qrService.generateQr(userId, content);
    }


}
