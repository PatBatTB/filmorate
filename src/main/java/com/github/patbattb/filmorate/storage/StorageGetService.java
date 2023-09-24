package com.github.patbattb.filmorate.storage;

import com.github.patbattb.filmorate.exception.FilmNotFoundException;
import com.github.patbattb.filmorate.model.Film;
import com.github.patbattb.filmorate.storage.film.FilmStorageDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageGetService {

    private final FilmStorageDao filmStorageDao;


    public Film getFilm(int id) throws FilmNotFoundException {
        Optional<Film> optional = filmStorageDao.getAll().stream()
                .filter(film -> film.getId() == id)
                .findFirst();
        return optional.orElseThrow(() -> new FilmNotFoundException("Film not found."));
    }
}
