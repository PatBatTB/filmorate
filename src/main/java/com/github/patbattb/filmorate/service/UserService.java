package com.github.patbattb.filmorate.service;

import com.github.patbattb.filmorate.exception.UserAlreadyExistsException;
import com.github.patbattb.filmorate.exception.UserNotFoundException;
import com.github.patbattb.filmorate.exception.FilmorateValidationException;
import com.github.patbattb.filmorate.model.User;
import com.github.patbattb.filmorate.storage.StorageGetService;
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
    StorageGetService storageGetService;

    public Collection<User> getAll() {
        return userStorage.getAll();
    }

    public User getUserById(int id) throws UserNotFoundException {
        Optional<User> optionalUser = userStorage.getAll().stream()
            .filter(p -> p.getId() == id)
            .findFirst();
        return optionalUser.orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    public User post(@Valid @RequestBody User user) throws UserAlreadyExistsException {
        userStorage.add(user);
        user.setId(getNextId());
        log.debug("A user with id {} has been added.", user.getId());
        return user;
    }

    public User put(@Valid @RequestBody User user) {
        Optional<Integer> optionalInt = userStorage.getAll().stream()
            .filter(user::equals)
            .map(User::getId)
            .findFirst();
        user.setId(optionalInt.orElseGet(UserService::getNextId));
        userStorage.update(user);
        log.debug("A user with id {} has been putted.", user.getId());
        return user;
    }

    public static int getNextId() {
        return ++id;
    }

    public Collection<User> getFriendsList(int id) throws UserNotFoundException {
        User user;
        user = storageGetService.getUser(id);
        return userStorage.getAll().stream()
                .filter(u -> user.getFriendList().contains(u.getId()))
                .collect(Collectors.toSet());
    }

    public User addFriend(int id, int friendId) throws FilmorateValidationException, UserNotFoundException {
        User user;
        User friend;
        if (id == friendId) throw new FilmorateValidationException("The user ID cannot be the same as a friend's ID");
        user = storageGetService.getUser(id);
        friend = storageGetService.getUser(friendId);
        user.getFriendList().add(friendId);
        friend.getFriendList().add(id);
        return user;
    }

    public User removeFriend(int id, int friendId) throws FilmorateValidationException, UserNotFoundException{
        User user;
        User friend;
        if (id == friendId) throw new FilmorateValidationException("The user ID cannot be the same as a friend's ID");
        user = storageGetService.getUser(id);
        friend = storageGetService.getUser(friendId);
        user.getFriendList().remove(friendId);
        friend.getFriendList().remove(id);
        return user;
    }

    public Collection<User> getCommonFriends(int id, int otherId) throws FilmorateValidationException, UserNotFoundException{
        User user;
        User otherUser;
        if (id == otherId) throw new FilmorateValidationException("The user ID cannot be the same as a friend's ID");
        user = storageGetService.getUser(id);
        otherUser = storageGetService.getUser(otherId);
        Set<Integer> commonFriends = new HashSet<>(user.getFriendList());
        commonFriends.retainAll(otherUser.getFriendList());
        return userStorage.getAll().stream()
                .filter(u -> commonFriends.contains(u.getId()))
                .collect(Collectors.toSet());
    }
}
