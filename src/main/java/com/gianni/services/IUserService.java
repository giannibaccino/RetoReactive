package com.gianni.services;

import com.gianni.models.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

public interface IUserService{
    Mono<User> create(User user);
    Mono<User> editPassword(String userId, String userPassword);
    Mono<User> editUsername(String userId, String username);
    Mono<User> editCreationDate(String userId, String userDate);
    Flux<User> findAll();
    Mono<User> findById(String userId);
    Mono<Void> delete(String userId);
}
