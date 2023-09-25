package com.github.patbattb.filmorate.storage.film;

import com.github.patbattb.filmorate.model.Film;
import lombok.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Component
@Value
@Primary
public class InMemoryFilmStorage implements FilmStorageDao {

    Set<Film> films;

    @Override
    public Collection<Film> getAll() {
        return Set.copyOf(films);
    }

    @Override
    public Optional<Film> getById(int id) {
        return films.stream()
                .filter(film -> id == film.getId())
                .findFirst();
    }

    @Override
    public Optional<Film> getByTitleAndDate(String title, LocalDate releaseDate) {
        return Optional.empty();
    }

    @Override
    public Optional<Film> add(Film film) {
        if (films.contains(film)) return Optional.empty();
        films.add(film);
        return Optional.of(film);
    }

    @Override
    public Optional<Film> update(Film film) {
        films.remove(film);
        films.add(film);
        return Optional.of(film);
    }

    @Override
    public Optional<Film> remove(Film film){
        if (!films.contains(film)) return Optional.empty();
        films.remove(film);
        return Optional.of(film);
    }

    @Override
    public Optional<Integer> getMaxFilmId() {
        return Optional.empty();
    }
}
