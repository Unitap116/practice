package com.unitap.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.unitap.config.properties.QrProperties;
import com.unitap.exception.card.GenerateQrException;
import com.unitap.service.QrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BaseQrService implements QrService {
    private final QrProperties qrProperties;

    @Override
    public byte[] generateQr(UUID userId, String content) {
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(
                    content,
                    BarcodeFormat.QR_CODE,
                    qrProperties.getWidth(),
                    qrProperties.getHeight()
            );
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, qrProperties.getImageFormat(), outputStream);

            return outputStream.toByteArray();
        } catch (WriterException
                | IOException e) {
            throw new GenerateQrException(userId);
        }
    }
}
