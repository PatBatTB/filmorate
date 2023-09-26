package com.github.patbattb.filmorate.service.film;

import com.github.patbattb.filmorate.exception.FilmNotFoundException;
import com.github.patbattb.filmorate.exception.NotFoundException;
import com.github.patbattb.filmorate.model.Film;
import com.github.patbattb.filmorate.model.Genre;
import com.github.patbattb.filmorate.storage.film.FilmStorageDao;
import com.github.patbattb.filmorate.storage.film.genre.GenreDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreDao genreDao;
    private final FilmStorageDao filmStorageDao;

    public Genre getById(int id) throws NotFoundException {
        return genreDao.getById(id).orElseThrow(() -> new NotFoundException("The genre not found."));
    }

    public Collection<Genre> getGenreListByFilmId(int id) throws FilmNotFoundException {
        return genreDao.getGenreListByFilm(id);
    }
}
