package com.unitap.exception.card;

import com.unitap.exception.AccessDeniedException;

public class CardAccessDeniedException extends AccessDeniedException {
    public CardAccessDeniedException(String userId) {
        super("Access denied for card with user id: %s - insufficient permissions".formatted(userId));
    }
}
