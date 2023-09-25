package com.github.patbattb.filmorate.storage.film;

import com.github.patbattb.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface FilmStorageDao {

    Collection<Film> getAll();

    Optional<Film> getById(int id);

    Optional<Film> getByTitleAndDate(String title, LocalDate releaseDate);

    Optional<Film> add(Film film);

    Optional<Film> update(Film film);

    Optional<Film> remove(Film film);

    Optional<Integer> getMaxFilmId();
}
