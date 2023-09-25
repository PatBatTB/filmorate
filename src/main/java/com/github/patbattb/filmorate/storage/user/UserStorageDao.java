package com.github.patbattb.filmorate.storage.user;

import com.github.patbattb.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorageDao {

    Collection<User> getAll();

    Optional<User> getById(int id);

    Optional<User> getByEmail(String email);

    Optional<User> add(User user);

    Optional<User> update(User user);

    Optional<User> remove(User user);

    Optional<Integer> getMaxUserId();
}
