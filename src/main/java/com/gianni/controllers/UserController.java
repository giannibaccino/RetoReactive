package com.gianni.controllers;

import com.gianni.models.User;
import com.gianni.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Comparator;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl service;

    @PostMapping
    public Mono<User> create(@RequestBody User user){
        return service.create(user);
    }

    @PutMapping("/changePassword/{userId}")
    public Mono<User> editPassword(@PathVariable("userId") String userId, @RequestBody String userPassword){
        return service.editPassword(userId, userPassword);
    }

    @PutMapping("/changeUsername/{userId}")
    public Mono<User> editUsername(@PathVariable("userId") String userId, @RequestBody String username){
        return service.editUsername(userId, username);
    }

    @PutMapping("/changeDate/{userId}")
    public Mono<User> editCreationSate(@PathVariable("userId") String userId, @RequestBody String userDate){
        return service.editCreationDate(userId, userDate);
    }

    @DeleteMapping("/{userId}")
    public Mono<Void> delete(@PathVariable("userId") String userId){
        return service.delete(userId);
    }

    @GetMapping
    public Flux<User> listAll(){
        return service.findAll();
    }

    @GetMapping("/getUser/{userId}")
    public Mono<User> listById(@PathVariable("userId") String userId){
        return service.findById(userId);
    }

    @GetMapping("/10older")
    public Flux<User> list10Older(){
        Comparator comparator = Comparator.comparing(User::getCreationDate);

        return service.findAll()
                .sort(comparator)
                .map(user -> user)
                .takeLast(10)
                .sort(comparator.reversed());
    }

    @GetMapping("/10newer")
    public Flux<User> list10Newer(){
        Comparator comparator = Comparator.comparing(User::getCreationDate);

        return service.findAll()
                .sort(comparator)
                .map(user -> user)
                .take(10);
    }

    @GetMapping("/oldest")
    public Mono<User> listOldest(){
        return service.findAll()
                .reduce((user, user2) -> user.getCreationDate().isAfter(user2.getCreationDate()) ? user : user2);
    }

    @GetMapping("/newest")
    public Mono<User> listNewest(){
        return service.findAll()
                .reduce((user, user2) -> user.getCreationDate().isBefore(user2.getCreationDate()) ? user : user2);
    }

    @GetMapping("/from/{date}")
    public Flux<User> listFrom(@PathVariable("date") String localDate){
        return service.findAll()
                .filter(user -> user.getCreationDate().equals(LocalDate.parse(localDate)))
                .map(user -> user);
    }
}
