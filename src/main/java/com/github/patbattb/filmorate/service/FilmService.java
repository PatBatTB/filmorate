package com.github.patbattb.filmorate.service;

import com.github.patbattb.filmorate.exception.FilmAlreadyExistsException;
import com.github.patbattb.filmorate.exception.FilmNotFoundException;
import com.github.patbattb.filmorate.model.Film;
import com.github.patbattb.filmorate.storage.film.FilmStorage;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
@Value
public class FilmService {

    private static int id = 0;
    FilmStorage filmStorage;

    public Collection<Film> get() {
        return filmStorage.getAll();
    }

    public Film getFilmById(int id) {
        Optional<Film> optionalFilm = filmStorage.getAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        try {
            return optionalFilm.orElseThrow(() -> new FilmNotFoundException("The film not found."));
        }
        catch (FilmNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Film post(Film film) {
        try {
            if (film == null) throw new IllegalArgumentException("The film from request cannot be null.");
            filmStorage.add(film);
            film.setId(getNextId());
            log.debug("A film with id {} has been added.", film.getId());
        } catch (FilmAlreadyExistsException | IllegalArgumentException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return film;
    }

    public Film put(Film film) {
        try {
            if (film == null) throw new IllegalArgumentException("The film from request cannot be null.");
            Optional<Integer> optionalInt = filmStorage.getAll().stream()
                    .filter(film::equals)
                    .map(Film::getId)
                    .findFirst();
            film.setId(optionalInt.orElseGet(FilmService::getNextId));
            filmStorage.update(film);
            log.debug("A film with id {} has been putted.", film.getId());
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return film;
    }

    public static int getNextId() {
        return ++id;
    }
}
