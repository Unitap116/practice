package com.unitap.service;

import com.unitap.dto.response.CardResponse;

import java.util.UUID;

public interface CardService {

    CardResponse getById(UUID cardId);

    byte[] getQrById(UUID cardId);
}
