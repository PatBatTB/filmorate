package com.github.patbattb.filmorate.service.film;

import com.github.patbattb.filmorate.exception.FilmAlreadyExistsException;
import com.github.patbattb.filmorate.exception.FilmNotFoundException;
import com.github.patbattb.filmorate.model.Film;
import com.github.patbattb.filmorate.storage.film.FilmStorageDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
public class FilmService {

    private final FilmStorageDao filmStorageDao;

    public FilmService(@Qualifier("filmStorageDaoImpl") FilmStorageDao filmStorageDao) {
        this.filmStorageDao = filmStorageDao;
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
}
