package com.github.patbattb.filmorate.storage.film;

import com.github.patbattb.filmorate.exception.FilmAlreadyExistsException;
import com.github.patbattb.filmorate.exception.FilmNotFoundException;
import com.github.patbattb.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorageDao {

    Collection<Film> getAll();
    void add(Film film) throws FilmAlreadyExistsException;

    void update(Film film);

    void remove(Film film) throws FilmNotFoundException;
}
