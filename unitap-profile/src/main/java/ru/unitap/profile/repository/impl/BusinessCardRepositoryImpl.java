package ru.unitap.profile.repository.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.unitap.profile.entity.BusinessCardEntity;
import ru.unitap.profile.repository.BusinessCardRepository;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Repository
@RequiredArgsConstructor
public class BusinessCardRepositoryImpl implements BusinessCardRepository {

  @Value("${firestore.collection.business-card}")
  private String collectionName;

  private final Firestore firestore;

  @Override
  public BusinessCardEntity save(BusinessCardEntity businessCard) {
    try {
      ApiFuture<QuerySnapshot> queryFuture = firestore.collection(collectionName)
        .whereEqualTo("userId", businessCard.getUserId())
        .get();

      QuerySnapshot snapshot = queryFuture.get();

      String documentId;

      if (!snapshot.isEmpty()) {
        documentId = snapshot.getDocuments().getFirst().getId();
      } else {
        documentId = UUID.randomUUID().toString();
      }

      businessCard.setDocumentId(documentId);

      ApiFuture<WriteResult> writeFuture = firestore.collection(collectionName)
        .document(documentId)
        .set(businessCard);

      writeFuture.get();

      return businessCard;

    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException("Failed to save business card: " + e.getMessage(), e);
    }
  }
}
