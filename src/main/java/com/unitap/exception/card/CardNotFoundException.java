package com.unitap.exception.card;

import com.unitap.exception.NotFoundException;

public class CardNotFoundException extends NotFoundException {
    public CardNotFoundException(String userId) {
        super("Card not found with user id: %s".formatted(userId));
    }
}
