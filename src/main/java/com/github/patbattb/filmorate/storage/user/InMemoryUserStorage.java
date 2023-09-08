package com.github.patbattb.filmorate.storage.user;

import com.github.patbattb.filmorate.exception.UserAlreadyExistsException;
import com.github.patbattb.filmorate.exception.UserNotFoundException;
import com.github.patbattb.filmorate.model.User;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
@Value
public class InMemoryUserStorage implements UserStorage {

    Set<User> users;

    @Override
    public Collection<User> getAll() {
        return users;
    }

    @Override
    public void add(User user) throws UserAlreadyExistsException {
        if (users.contains(user)) throw new UserAlreadyExistsException("The user already exists.");
        users.add(user);
    }

    @Override
    public void update(User user) {
        users.remove(user);
        users.add(user);
    }

    @Override
    public void remove(User user) throws UserNotFoundException {
        if (!users.contains(user)) throw new UserNotFoundException("The user not found.");
        users.remove(user);
    }
}
