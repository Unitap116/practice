package com.unitap.exception.card;

import com.unitap.exception.InternalServerErrorException;

import java.util.UUID;

public class GenerateQrException extends InternalServerErrorException {
    public GenerateQrException(UUID cardId) {
        super("Couldn't generate QR code for user with id: %s".formatted(cardId));
    }
}
