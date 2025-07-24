package com.unitap.service;


import com.unitap.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<UserDto> getAllUsers();

    Mono<Void> deleteUserById(String id);

}
