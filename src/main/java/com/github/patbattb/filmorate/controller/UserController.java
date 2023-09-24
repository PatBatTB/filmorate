package com.github.patbattb.filmorate.controller;

import com.github.patbattb.filmorate.exception.UserAlreadyExistsException;
import com.github.patbattb.filmorate.exception.UserNotFoundException;
import com.github.patbattb.filmorate.exception.FilmorateValidationException;
import com.github.patbattb.filmorate.model.User;
import com.github.patbattb.filmorate.service.user.FriendService;
import com.github.patbattb.filmorate.service.user.UserService;
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
    FriendService friendService;

    @GetMapping
    Collection<User> getAll() {
        return userService.getAll();
    }

    @PostMapping
    User post(@Valid @RequestBody User user) throws UserAlreadyExistsException {
        return userService.post(user);
    }

    @PutMapping
    User put(@Valid @RequestBody User user) {
        return userService.put(user);
    }

    @GetMapping("/{id}")
    User getUserById(@PathVariable int id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    User delete(@PathVariable int id) throws UserNotFoundException {
        return userService.deleteUserById(id);
    }

    @GetMapping("/{id}/friends")
    Collection<User> getFriendsList(@PathVariable int id) throws UserNotFoundException {
        return friendService.getFriendsList(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    User addFriend(@PathVariable int id, @PathVariable int friendId)
            throws UserNotFoundException, FilmorateValidationException, UserAlreadyExistsException {
        return friendService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    User deleteFriend(@PathVariable int id, @PathVariable int friendId)
            throws UserNotFoundException, FilmorateValidationException {
        return friendService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    Collection<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId)
            throws UserNotFoundException, FilmorateValidationException {
        return friendService.getCommonFriends(id, otherId);
    }
}
