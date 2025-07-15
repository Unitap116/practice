package ru.itis.unitap.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.itis.unitap.entity.User;

@Repository
public interface UserRepository extends FirestoreReactiveRepository<User> {

    Mono<User> findByEmail(String email);
}
