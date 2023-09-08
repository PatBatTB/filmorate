package com.github.patbattb.filmorate.storage.user;

import com.github.patbattb.filmorate.exception.UserAlreadyExistsException;
import com.github.patbattb.filmorate.exception.UserNotFoundException;
import com.github.patbattb.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    Collection<User> getAll();
    void add(User user) throws UserAlreadyExistsException;

    void update(User user);

    void remove(User user) throws UserNotFoundException;

}
