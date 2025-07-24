package com.unitap.repository.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.unitap.entity.ProfileEntity;
import com.unitap.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Repository
@RequiredArgsConstructor
public class ProfileRepositoryImpl implements ProfileRepository {

  @Value("${firestore.collection.profile}")
  private String collectionName;

  private final Firestore firestore;

  @Override
  public Optional<ProfileEntity> getById(UUID id) {
    ApiFuture<QuerySnapshot> profiles = firestore
      .collection(collectionName)
      .whereEqualTo("id", id.toString())
      .get();
    try {
      QuerySnapshot querySnapshot = profiles.get();
      if (!querySnapshot.isEmpty()) {
        DocumentSnapshot doc = querySnapshot.getDocuments().getFirst();
        return Optional.of(doc.toObject(ProfileEntity.class));
      } else {
        return Optional.empty();
      }
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public ProfileEntity save(ProfileEntity profileEntity) {
    try {
      ApiFuture<QuerySnapshot> queryFuture = firestore.collection(collectionName)
        .whereEqualTo("id", profileEntity.getId())
        .get();

      QuerySnapshot snapshot = queryFuture.get();

      String documentId;
      if (!snapshot.isEmpty()) {
        documentId = snapshot.getDocuments().getFirst().getId();
      } else {
        documentId = UUID.randomUUID().toString();
      }

      ApiFuture<WriteResult> writeFuture =
        firestore.collection(collectionName)
          .document(documentId)
          .set(profileEntity);

      writeFuture.get();

      return profileEntity;

    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException("Failed to save profile: " + e.getMessage(), e);
    }
  }
}
