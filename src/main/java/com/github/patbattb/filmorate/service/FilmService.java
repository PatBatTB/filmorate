package com.github.patbattb.filmorate.service;

import com.github.patbattb.filmorate.exception.FilmAlreadyExistsException;
import com.github.patbattb.filmorate.exception.FilmNotFoundException;
import com.github.patbattb.filmorate.exception.NotFoundException;
import com.github.patbattb.filmorate.model.Film;
import com.github.patbattb.filmorate.storage.StorageGetService;
import com.github.patbattb.filmorate.storage.film.FilmStorage;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Value
public class FilmService {

    private static int id = 0;
    FilmStorage filmStorage;
    StorageGetService storageGetService;

    public Collection<Film> get() {
        return filmStorage.getAll();
    }

    public Film getFilmById(int id) throws FilmNotFoundException {
        Optional<Film> optionalFilm = filmStorage.getAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        return optionalFilm.orElseThrow(() -> new FilmNotFoundException("The film not found."));
    }

    public Film post(Film film) throws FilmAlreadyExistsException {
            filmStorage.add(film);
            film.setId(getNextId());
            log.debug("A film with id {} has been added.", film.getId());
        return film;
    }

    public Film put(Film film) {
            Optional<Integer> optionalInt = filmStorage.getAll().stream()
                    .filter(film::equals)
                    .map(Film::getId)
                    .findFirst();
            film.setId(optionalInt.orElseGet(FilmService::getNextId));
            filmStorage.update(film);
            log.debug("A film with id {} has been putted.", film.getId());
        return film;
    }

    public static int getNextId() {
        return ++id;
    }

    public Film addLike(int id, int userId) throws NotFoundException {
        Film film;
        storageGetService.getUser(userId);
        film = storageGetService.getFilm(id);
        film.getLikes().add(userId);
        return film;
    }

    public Film removeLike(int id, int userId) throws NotFoundException {
        Film film;
        storageGetService.getUser(userId);
        film = storageGetService.getFilm(id);
        film.getLikes().remove(userId);
        return film;
    }

    public Collection<Film> getPopularFilms(int count) {
        return filmStorage.getAll().stream()
                .sorted(Comparator.comparing(Film::getLikesCount, Comparator.reverseOrder()))
                .limit(count)
                .collect(Collectors.toList());
    }

}
