package com.unitap.exception.card;

import com.unitap.exception.InternalServerErrorException;

public class GenerateQrException extends InternalServerErrorException {
    public GenerateQrException(String cardId) {
        super("Couldn't generate QR code for user with id: %s".formatted(cardId));
    }
}
