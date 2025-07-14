package com.unitap.controller;

import com.unitap.dto.response.CardResponse;
import com.unitap.service.impl.BaseCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/card")
@RestController
public class CardController {

    private final BaseCardService cardService;

    @GetMapping("/{cardId}")
    public CardResponse getCard(@PathVariable UUID cardId) {
        return cardService.getById(cardId);
    }

    @GetMapping("/{cardId}/qr")
    public String getCardQr(@PathVariable UUID cardId) {
        return cardService.getQrById(cardId);
    }
}
