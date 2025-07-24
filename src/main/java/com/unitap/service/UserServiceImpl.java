package com.unitap.service;

import com.unitap.dto.UserDto;
import com.unitap.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public Flux<UserDto> getAllUsers() {
        return userRepository.findAll()
                .map(user -> new UserDto()
                        .setId(user.getId())
                        .setEmail(user.getEmail())
                        .setRole(user.getRole().toString()));
    }

    @Override
    public Mono<Void> deleteUserById(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")))
                .flatMap(user -> userRepository.deleteById(user.getId()));
    }
}
