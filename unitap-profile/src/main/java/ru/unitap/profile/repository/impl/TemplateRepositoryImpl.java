package ru.unitap.profile.repository.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.unitap.profile.repository.TemplateRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Repository
@RequiredArgsConstructor
public class TemplateRepositoryImpl implements TemplateRepository {

  @Value("${firestore.collection.template}")
  private String collectionName;

  private final Firestore firestore;

  @Override
  public Optional<Object> getById(UUID id) {
    ApiFuture<QuerySnapshot> templates = firestore
      .collection(collectionName)
      .whereEqualTo("id", id.toString())
      .get();
    try {
      QuerySnapshot querySnapshot = templates.get();
      if (!querySnapshot.isEmpty()) {
        return Optional.of(new Object());
      } else {
        return Optional.empty();
      }
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}
