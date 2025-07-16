package com.unitap.controller;

import com.unitap.dto.response.CardResponse;
import com.unitap.service.impl.BaseCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/card")
@RestController
public class CardController {

    private final BaseCardService cardService;

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public CardResponse getCard(@PathVariable String userId) {
        return cardService.getById(userId);
    }

    @GetMapping(value = "/{userId}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getCardQr(@PathVariable String userId) {
        return cardService.getQrById(userId);
    }
}
