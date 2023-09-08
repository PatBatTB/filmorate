package com.github.patbattb.filmorate.storage.film;

import com.github.patbattb.filmorate.exception.FilmAlreadyExistsException;
import com.github.patbattb.filmorate.exception.FilmNotFoundException;
import com.github.patbattb.filmorate.model.Film;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
@Value
public class InMemoryFilmStorage implements FilmStorage {

    Set<Film> films;

    @Override
    public Collection<Film> getAll() {
        return films;
    }

    @Override
    public void add(Film film) throws FilmAlreadyExistsException {
        if (films.contains(film)) throw new FilmAlreadyExistsException("The film is already exist.");
        films.add(film);
    }

    @Override
    public void update(Film film) {
        films.remove(film);
        films.add(film);
    }

    @Override
    public void remove(Film film) throws FilmNotFoundException {
        if (!films.contains(film)) throw new FilmNotFoundException("The film not found.");
        films.remove(film);
    }
}
