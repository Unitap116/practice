package com.unitap.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.unitap.config.properties.QrProperties;
import com.unitap.dto.response.CardResponse;
import com.unitap.service.CardService;
import com.unitap.service.QrService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class CardControllerTest {

    @Mock
    private Firestore firestore;

    @Mock
    private CollectionReference collectionReference;

    @Mock
    private DocumentReference documentReference;

    @Mock
    private ApiFuture<DocumentSnapshot> apiFuture;

    @MockitoBean
    private CardService cardService;

    @Autowired
    private CardController cardController;

    @Autowired
    private QrService qrService;

    @Autowired
    private QrProperties qrProperties;

    @BeforeEach
    void setUp() {
        when(firestore.collection(anyString()))
                .thenReturn(collectionReference);
        when(collectionReference.document(anyString()))
                .thenReturn(documentReference);
        when(documentReference.get())
                .thenReturn(apiFuture);
    }

    @Test
    void getCardTest() {
        String expectedId = UUID.randomUUID().toString();
        String expectedName = "Name";
        Boolean expectedIsPublic = Boolean.TRUE;
        Long expectedViews = 123L;

        CardResponse expectedResult = new CardResponse(expectedId, expectedName, expectedIsPublic, expectedViews);

        when(cardService.getById(expectedId)).thenReturn(expectedResult);

        CardResponse actualResult = cardController.getCard(expectedId);

        assertNotNull(actualResult);
        assertEquals(actualResult, expectedResult);
    }


    @Test
    void getCardQrTest() {
        String expectedId = UUID.randomUUID().toString();
        byte[] expectedResult = qrProperties.getLinkPattern().formatted(expectedId).getBytes();

        when(cardService.getQrById(expectedId)).thenReturn(expectedResult);

        byte[] actualResult = cardController.getCardQr(expectedId);

        assertNotNull(actualResult);
        assertEquals(actualResult, expectedResult);
    }

}
