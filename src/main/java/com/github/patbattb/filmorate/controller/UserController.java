package com.github.patbattb.filmorate.controller;

import com.github.patbattb.filmorate.exception.UserAlreadyExist;
import com.github.patbattb.filmorate.model.User;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Value
@RestController
@RequestMapping("/users")
public class UserController {

    @Getter(AccessLevel.NONE)
    Set<User> users = new HashSet<>();

    @GetMapping
    Set<User> get() {
        return users;
    }

    @PostMapping
    User post(@Valid @RequestBody User user) {
        try {
            if (user == null) throw new IllegalArgumentException("The user cannot be null.");
            if (users.contains(user)) throw new UserAlreadyExist("The user is already exist.");
            users.add(user);
            log.debug("A user with id {} has been added.", user.getId());

        } catch (UserAlreadyExist | IllegalArgumentException e) {
            log.warn(e.getMessage());
        }
        return user;
    }

    @PutMapping
    User put(@Valid @RequestBody User user) {
        try {
            if (user == null) throw new IllegalArgumentException("The user from request cannot be null");
            users.add(user);
            log.debug("A user with id {} has been putted.", user.getId());
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
        }
        return user;
    }

}
