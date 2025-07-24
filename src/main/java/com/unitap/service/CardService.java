package com.unitap.service;

import com.unitap.dto.response.CardResponse;

public interface CardService {

    CardResponse getById(String userId);

    byte[] getQrById(String userId);
}
