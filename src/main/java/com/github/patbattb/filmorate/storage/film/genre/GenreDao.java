package com.github.patbattb.filmorate.storage.film.genre;

import com.github.patbattb.filmorate.model.Genre;

import java.util.Collection;
import java.util.Optional;

public interface GenreDao {

    Optional<Genre> getById(int id);

    Collection<Genre> getGenreListByFilm(int id);
}
