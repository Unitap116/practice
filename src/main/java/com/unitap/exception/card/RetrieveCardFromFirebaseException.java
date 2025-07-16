package com.unitap.exception.card;

import com.unitap.exception.InternalServerErrorException;

public class RetrieveCardFromFirebaseException extends InternalServerErrorException {
    public RetrieveCardFromFirebaseException(String userId) {
        super("Couldn't retrieve card from Firebase with user id: %s".formatted(userId));
    }
}
