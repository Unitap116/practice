package com.unitap.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.unitap.config.properties.QrProperties;
import com.unitap.dto.response.CardResponse;
import com.unitap.entity.Card;
import com.unitap.exception.card.CardAccessDeniedException;
import com.unitap.exception.card.CardNotFoundException;
import com.unitap.mapper.CardMapper;
import com.unitap.service.impl.BaseCardService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class BaseCardServiceTest {

    @Mock
    private Firestore firestore;

    @Mock
    private CollectionReference collectionReference;

    @Mock
    private DocumentReference documentReference;

    @Mock
    private ApiFuture<DocumentSnapshot> apiFuture;

    @Mock
    private DocumentSnapshot documentSnapshot;

    @InjectMocks
    private BaseCardService cardService;

    @Mock
    private CardMapper cardMapper;

    @Autowired
    private QrService qrService;

    @Autowired
    private QrProperties qrProperties;

    @Test
    public void getCardByIdTest_whenCardExist() throws ExecutionException, InterruptedException {
        String expectedId = UUID.randomUUID().toString();
        String expectedName = "Name";
        Boolean expectedIsPublic = Boolean.TRUE;
        Long expectedViews = 123L;
        Card expectedEntity = new Card(expectedId, expectedName, expectedIsPublic, expectedViews);
        CardResponse expectedResult = new CardResponse(expectedId, expectedName, expectedIsPublic, expectedViews);

        when(firestore.collection(anyString())).thenReturn(collectionReference);
        when(collectionReference.document(anyString())).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(apiFuture);
        when(apiFuture.get()).thenReturn(documentSnapshot);
        when(documentSnapshot.toObject(Card.class)).thenReturn(expectedEntity);
        when(documentSnapshot.exists()).thenReturn(true);
        when(cardMapper.toResponse(expectedEntity)).thenReturn(expectedResult);

        CardResponse actualResult = cardService.getById(expectedId);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getCardByIdTest_whenCardDoesNotExist() throws ExecutionException, InterruptedException {
        String randomId = UUID.randomUUID().toString();

        when(firestore.collection(anyString())).thenReturn(collectionReference);
        when(collectionReference.document(randomId)).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(apiFuture);
        when(apiFuture.get()).thenReturn(documentSnapshot);
        when(documentSnapshot.toObject(Card.class)).thenReturn(null);
        when(documentSnapshot.exists()).thenReturn(false);

        CardNotFoundException ex = assertThrows(CardNotFoundException.class, () -> {cardService.getById(randomId);});

        assertEquals(CardNotFoundException.class, ex.getClass());
    }

    @Test
    public void getCardByIdTest_whenCardAccessDenied() throws ExecutionException, InterruptedException {
        String expectedId = UUID.randomUUID().toString();
        String expectedName = "Name";
        Boolean expectedIsPublic = Boolean.FALSE;
        Long expectedViews = 123L;
        Card expectedEntity = new Card(expectedId, expectedName, expectedIsPublic, expectedViews);

        when(firestore.collection(anyString())).thenReturn(collectionReference);
        when(collectionReference.document(anyString())).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(apiFuture);
        when(apiFuture.get()).thenReturn(documentSnapshot);
        when(documentSnapshot.toObject(Card.class)).thenReturn(expectedEntity);
        when(documentSnapshot.exists()).thenReturn(true);

        CardAccessDeniedException ex = assertThrows(CardAccessDeniedException.class, () -> {cardService.getById(expectedId);});

        assertEquals(CardAccessDeniedException.class, ex.getClass());
    }


    @Test
    void getCardQrTest() {
        String expectedId = UUID.randomUUID().toString();
        String expectedResult = qrProperties.getLinkPattern().formatted(expectedId);

        /* first of all - encode message and after it decode and compare with first version */
        byte[] actualResult = qrService.generateQr(expectedId);
        String actualResultString = decodeQrCode(actualResult);

        assertEquals(expectedResult, actualResultString);
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
