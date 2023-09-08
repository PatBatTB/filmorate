package com.github.patbattb.filmorate.controller;

import com.github.patbattb.filmorate.model.User;
import com.github.patbattb.filmorate.service.UserService;
import jakarta.validation.Valid;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@Value
@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @GetMapping
    Collection<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    User post(@Valid @RequestBody User user) {
        return userService.post(user);
    }

    @PutMapping
    User put(@Valid @RequestBody User user) {
        return userService.put(user);
    }

}
