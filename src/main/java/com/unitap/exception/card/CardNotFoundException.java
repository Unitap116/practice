package com.unitap.exception.card;

import com.unitap.exception.NotFoundException;

import java.util.UUID;

public class CardNotFoundException extends NotFoundException {
    public CardNotFoundException(UUID cardId) {
        super("Card not found with id: %s".formatted(cardId));
    }
}
