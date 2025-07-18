package com.unitap.service;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.unitap.config.properties.QrProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class BaseQrServiceTest {

    @Autowired
    private QrService qrService;

    @Autowired
    private QrProperties qrProperties;

    @Test
    public void generateQrTest() {
        String randomId = UUID.randomUUID().toString();
        String expectedResult = qrProperties.getLinkPattern().formatted(randomId);

        byte[] actualResult = qrService.generateQr(randomId);
        String actualResultString = decodeQrCode(actualResult);

        assertNotNull(actualResult);
        assertEquals(actualResultString, expectedResult);
    }

    private String decodeQrCode(byte[] qrCodeBytes) {
        BinaryBitmap bb = null;
        try {
            bb = new BinaryBitmap(new HybridBinarizer(
                    new BufferedImageLuminanceSource(
                            ImageIO.read(new ByteArrayInputStream(qrCodeBytes))
                    )
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Result result = null;
        try {
            result = new MultiFormatReader().decode(bb);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        return result.getText();
    }
}
