package ru.itis.unitap.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.itis.unitap.dto.UserDto;

public interface UserService {

    Flux<UserDto> getAllUsers();

    Mono<Void> deleteUserById(String id);

}
