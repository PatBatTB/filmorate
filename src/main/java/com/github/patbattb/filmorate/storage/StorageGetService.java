package com.github.patbattb.filmorate.storage;

import com.github.patbattb.filmorate.exception.FilmNotFoundException;
import com.github.patbattb.filmorate.exception.UserNotFoundException;
import com.github.patbattb.filmorate.model.Film;
import com.github.patbattb.filmorate.model.User;
import com.github.patbattb.filmorate.storage.film.FilmStorage;
import com.github.patbattb.filmorate.storage.user.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageGetService {

    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

    public User getUser(int id) throws UserNotFoundException {
        Optional<User> optionalUser = userStorage.getAll().stream()
                .filter(user -> user.getId() == id)
                .findFirst();

        return optionalUser.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public Film getFilm(int id) throws FilmNotFoundException {
        Optional<Film> optional = filmStorage.getAll().stream()
                .filter(film -> film.getId() == id)
                .findFirst();
        return optional.orElseThrow(() -> new FilmNotFoundException("Film not found."));
    }
}
