package com.unitap.controller;

import com.unitap.api.CardApi;
import com.unitap.dto.response.CardResponse;
import com.unitap.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CardController implements CardApi {

    private final CardService cardService;

    @Override
    public CardResponse getCard(@PathVariable String userId) {
        return cardService.getById(userId);
    }

    @Override
    public byte[] getCardQr(@PathVariable String userId) {
        return cardService.getQrById(userId);
    }
}
