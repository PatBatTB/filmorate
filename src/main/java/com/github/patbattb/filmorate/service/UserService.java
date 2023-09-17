package com.github.patbattb.filmorate.service;

import com.github.patbattb.filmorate.exception.UserAlreadyExistsException;
import com.github.patbattb.filmorate.exception.UserNotFoundException;
import com.github.patbattb.filmorate.exception.ValidationException;
import com.github.patbattb.filmorate.model.User;
import com.github.patbattb.filmorate.storage.user.UserStorage;
import jakarta.validation.Valid;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Value
@Slf4j
@Service
public class UserService {

    private static int id = 0;
    UserStorage userStorage;

    public Collection<User> getAll() {
        return userStorage.getAll();
    }

    public User getUserById(int id) {
        Optional<User> optionalUser = userStorage.getAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        try {
            return optionalUser.orElseThrow(() -> new UserNotFoundException("User not found."));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public User post(@Valid @RequestBody User user) {
        try {
            if (user == null) throw new IllegalArgumentException("The user cannot be null.");
            userStorage.add(user);
            user.setId(getNextId());
            log.debug("A user with id {} has been added.", user.getId());

        } catch (UserAlreadyExistsException | IllegalArgumentException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return user;
    }

    public User put(@Valid @RequestBody User user) {
        try {
            if (user == null) throw new IllegalArgumentException("The user from request cannot be null");
            Optional<Integer> optionalInt = userStorage.getAll().stream()
                            .filter(user::equals)
                            .map(User::getId)
                            .findFirst();
            user.setId(optionalInt.orElseGet(UserService::getNextId));
            userStorage.update(user);
            log.debug("A user with id {} has been putted.", user.getId());
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return user;
    }

    public static int getNextId() {
        return ++id;
    }

    public Collection<User> getFriendsList(int id) {
        User user;
        try {
            user = getUser(id);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
        return userStorage.getAll().stream()
                .filter(u -> user.getFriendList().contains(u.getId()))
                .collect(Collectors.toSet());
    }

    public User addFriend(int id, int friendId) {
        User user;
        User friend;
        try {
            if (id == friendId) throw new ValidationException("The user ID cannot be the same as a friend's ID");
            user = getUser(id);
            friend = getUser(friendId);
        } catch (UserNotFoundException | ValidationException e) {
            throw new RuntimeException(e.getMessage());
        }
        user.getFriendList().add(friendId);
        friend.getFriendList().add(id);
        return user;
    }

    public User removeFriend(int id, int friendId) {
        User user;
        User friend;
        try {
            if (id == friendId) throw new ValidationException("The user ID cannot be the same as a friend's ID");
            user = getUser(id);
            friend = getUser(friendId);
        } catch (UserNotFoundException | ValidationException e) {
            throw new RuntimeException(e.getMessage());
        }
        user.getFriendList().remove(friendId);
        friend.getFriendList().remove(id);
        return user;
    }

    public Collection<User> getCommonFriends(int id, int otherId) {
        User user;
        User otherUser;
        try {
            user = getUser(id);
            otherUser = getUser(otherId);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
        Set<Integer> commonFriends = new HashSet<>(user.getFriendList());
        commonFriends.retainAll(otherUser.getFriendList());
        return userStorage.getAll().stream()
                .filter(u -> commonFriends.contains(u.getId()))
                .collect(Collectors.toSet());
    }

    private User getUser(int id) throws UserNotFoundException {
        Optional<User> optionalUser = userStorage.getAll().stream()
                .filter(user -> user.getId() == id)
                .findFirst();

        return optionalUser.orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
