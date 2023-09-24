package com.github.patbattb.filmorate.storage.film;

import com.github.patbattb.filmorate.exception.FilmAlreadyExistsException;
import com.github.patbattb.filmorate.exception.FilmNotFoundException;
import com.github.patbattb.filmorate.model.Film;

import java.util.Collection;

public class FilmStorageDaoImpl implements FilmStorageDao {
    @Override
    public Collection<Film> getAll() {
        return null;
    }

    @Override
    public void add(Film film) throws FilmAlreadyExistsException {

    }

    @Override
    public void update(Film film) {

    }

    @Override
    public void remove(Film film) throws FilmNotFoundException {

    }
}
