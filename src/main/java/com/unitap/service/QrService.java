package com.unitap.service;

import java.util.UUID;

public interface QrService {

    byte[] generateQr(UUID userId, String text);
}
