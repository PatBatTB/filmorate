package com.github.patbattb.filmorate.service.film;

import com.github.patbattb.filmorate.exception.FilmAlreadyExistsException;
import com.github.patbattb.filmorate.exception.FilmNotFoundException;
import com.github.patbattb.filmorate.exception.NotFoundException;
import com.github.patbattb.filmorate.exception.UserNotFoundException;
import com.github.patbattb.filmorate.model.Film;
import com.github.patbattb.filmorate.storage.StorageGetService;
import com.github.patbattb.filmorate.storage.film.FilmStorageDao;
import com.github.patbattb.filmorate.storage.user.UserStorageDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private static int id = 0;
    FilmStorageDao filmStorageDao;
    @Autowired
    @Qualifier("userStorageDaoImpl")
    UserStorageDao userStorageDao;
    StorageGetService storageGetService;

    public Collection<Film> get() {
        return filmStorageDao.getAll();
    }

    public Film getFilmById(int id) throws FilmNotFoundException {
        Optional<Film> optionalFilm = filmStorageDao.getAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        return optionalFilm.orElseThrow(() -> new FilmNotFoundException("The film not found."));
    }

    public Film post(Film film) throws FilmAlreadyExistsException {
            filmStorageDao.add(film);
            film.setId(getNextId());
            log.debug("A film with id {} has been added.", film.getId());
        return film;
    }

    public Film put(Film film) {
            Optional<Integer> optionalInt = filmStorageDao.getAll().stream()
                    .filter(film::equals)
                    .map(Film::getId)
                    .findFirst();
            film.setId(optionalInt.orElseGet(FilmService::getNextId));
            filmStorageDao.update(film);
            log.debug("A film with id {} has been putted.", film.getId());
        return film;
    }

    public static int getNextId() {
        return ++id;
    }

    public Film addLike(int filmId, int userId) throws NotFoundException {
        Film film;
        userStorageDao.findById(userId).orElseThrow(UserNotFoundException::new);
        film = storageGetService.getFilm(filmId);
        film.getLikes().add(userId);
        return film;
    }

    public Film removeLike(int id, int userId) throws NotFoundException {
        Film film;
        userStorageDao.findById(userId).orElseThrow(UserNotFoundException::new);
        film = storageGetService.getFilm(id);
        film.getLikes().remove(userId);
        return film;
    }

    public Collection<Film> getPopularFilms(int count) {
        return filmStorageDao.getAll().stream()
                .sorted(Comparator.comparing(Film::getLikesCount, Comparator.reverseOrder()))
                .limit(count)
                .collect(Collectors.toList());
    }

}
