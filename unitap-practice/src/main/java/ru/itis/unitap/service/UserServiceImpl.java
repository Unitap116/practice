package ru.itis.unitap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.itis.unitap.dto.UserDto;
import ru.itis.unitap.repository.UserRepository;

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
