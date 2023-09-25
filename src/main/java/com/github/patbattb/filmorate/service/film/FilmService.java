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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private final FilmStorageDao filmStorageDao;
    private final UserStorageDao userStorageDao;
    private final StorageGetService storageGetService;

    public FilmService(@Qualifier("filmStorageDaoImpl") FilmStorageDao filmStorageDao,
                       @Qualifier("userStorageDaoImpl") UserStorageDao userStorageDao,
                       StorageGetService storageGetService) {
        this.filmStorageDao = filmStorageDao;
        this.userStorageDao = userStorageDao;
        this.storageGetService = storageGetService;
    }

    private int getNextId() {
        int currentMaxId = filmStorageDao.getMaxFilmId().orElse(0);
        return ++currentMaxId;
    }

    public Collection<Film> get() {
        return filmStorageDao.getAll();
    }

    public Film getFilmById(int id) throws FilmNotFoundException {
        var optionalFilm = filmStorageDao.getById(id);
        return optionalFilm.orElseThrow(() -> new FilmNotFoundException("The film not found."));
    }

    public Film post(Film film) throws FilmAlreadyExistsException {
        if (filmStorageDao.getByTitleAndDate(film.getTitle(), film.getReleaseDate()).isPresent()) {
            throw new FilmAlreadyExistsException();
        }
        film.setId(getNextId());
        film = filmStorageDao.add(film).orElseThrow(RuntimeException::new);
            log.debug("A film with id {} has been added.", film.getId());
        return film;
    }

    public Film put(Film film) {
        var optionalFilm = filmStorageDao.getByTitleAndDate(film.getTitle(), film.getReleaseDate());
        if (optionalFilm.isEmpty()) {
            film.setId(getNextId());
            film = filmStorageDao.add(film).orElseThrow(RuntimeException::new);
        } else {
            film.setId(optionalFilm.get().getId());
            film = filmStorageDao.update(film).orElseThrow(RuntimeException::new);
        }
        log.debug("A film with id {} has been putted.", film.getId());
        return film;
    }

    public Film delete(int id) throws FilmNotFoundException {
        var optionalFilm = filmStorageDao.getById(id);
        optionalFilm.ifPresent(filmStorageDao::remove);
        return optionalFilm.orElseThrow(FilmNotFoundException::new);
    }

    public Film addLike(int filmId, int userId) throws NotFoundException {
        Film film;
        userStorageDao.getById(userId).orElseThrow(UserNotFoundException::new);
        film = storageGetService.getFilm(filmId);
        film.getLikes().add(userId);
        return film;
    }

    public Film removeLike(int id, int userId) throws NotFoundException {
        Film film;
        userStorageDao.getById(userId).orElseThrow(UserNotFoundException::new);
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
