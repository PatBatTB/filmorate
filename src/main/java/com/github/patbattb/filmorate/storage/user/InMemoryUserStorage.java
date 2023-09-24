package com.github.patbattb.filmorate.storage.user;

import com.github.patbattb.filmorate.model.User;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

@Component
@Value
public class InMemoryUserStorage implements UserStorageDao {

    Set<User> users;

    @Override
    public Collection<User> getAll() {
        return users;
    }

    @Override
    public Optional<User> findById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<User> add(User user) {
        if (users.contains(user)) return Optional.empty();
        users.add(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> update(User user) {
        users.remove(user);
        users.add(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> remove(User user) {
        if (!users.contains(user)) return Optional.empty();
        users.remove(user);
        return Optional.of(user);
    }

    @Override
    public Optional<Integer> getMaxUserId() {
        return users.stream()
                .map(User::getId)
                .max(Comparator.naturalOrder());
    }
}
