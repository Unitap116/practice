package com.unitap.service.impl;

import com.unitap.service.QrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BaseQrService implements QrService {
    @Override
    public String generateQr(String text) {
        return "";
    }
}
