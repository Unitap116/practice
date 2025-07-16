package com.unitap.service.impl;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.unitap.dto.response.CardResponse;
import com.unitap.entity.Card;
import com.unitap.exception.card.RetrieveCardFromFirebaseException;
import com.unitap.mapper.CardMapper;
import com.unitap.service.CardService;
import com.unitap.service.QrService;
import com.unitap.utils.FirebaseConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;


@Service
@RequiredArgsConstructor
@Slf4j
public class BaseCardService implements CardService {

    private final QrService qrService;
    private final Firestore firestore;
    private final CardMapper cardMapper;

    @Override
    public CardResponse getById(String userId) {
        try { /* TODO put in aspects */
            log.info("Running getById method for user with id: {}", userId); /* TODO put in aspects */
            DocumentReference cardReference = firestore.collection(FirebaseConstants.BUSINESS_CARDS_COLLECTION_NAME)
                    .document(userId);

            Card card = cardReference.get().get().toObject(Card.class);
            cardReference.update(FirebaseConstants.BUSINESS_CARDS_VIEWS_FIELD_NAME, FieldValue.increment(1));

            log.info("GetById method successfully finished with userId: {}", userId); /* TODO put in aspects */
            return cardMapper.toResponse(card);
        } catch (InterruptedException | ExecutionException e) { /* TODO put in aspects */
            log.error("Exception during running getById method for user with id: {}", userId);
            throw new RetrieveCardFromFirebaseException(userId);
        }
    }

    @Override
    public byte[] getQrById(String userId) {
        log.info("Running getQrById method for user with id: {}", userId); /* TODO put in aspects */
        return qrService.generateQr(userId);
    }
}
