package com.gianni.services;

import com.gianni.models.User;
import com.gianni.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserRepository repo;

    @Override
    public Mono<User> create(User user) {
        return repo.save(user);
    }

    @Override
    public Mono<User> editPassword(String userId, String userPassword) {
        return repo.findById(userId)
                .flatMap(user1 -> {
                    user1.setUserPassword(userPassword);
                    return repo.save(user1);
                });
    }

    @Override
    public Mono<User> editUsername(String userId, String username) {
        return repo.findById(userId)
                .flatMap(user1 -> {
                    user1.setUsername(username);
                    return repo.save(user1);
                });
    }

    @Override
    public Mono<User> editCreationDate(String userId, String userDate) {
        return repo.findById(userId)
                .flatMap(user1 -> {
                    user1.setCreationDate(LocalDate.parse(userDate));
                    return repo.save(user1);
                });
    }

    @Override
    public Flux<User> findAll() {
        return repo.findAll();
    }

    @Override
    public Mono<User> findById(String userId) {
        return repo.findById(userId);
    }

    @Override
    public Mono<Void> delete(String userId) {
        return repo.findById(userId)
                .flatMap(user -> repo.deleteById(user.getUserId()));
    }
}
