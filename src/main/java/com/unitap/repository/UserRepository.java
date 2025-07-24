package com.unitap.repository;


import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.unitap.entity.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface UserRepository extends FirestoreReactiveRepository<User> {

    Mono<User> findByEmail(String email);
}
