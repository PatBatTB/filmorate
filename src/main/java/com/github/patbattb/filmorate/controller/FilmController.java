package com.github.patbattb.filmorate.controller;

import com.github.patbattb.filmorate.exception.FilmAlreadyExistsException;
import com.github.patbattb.filmorate.exception.FilmNotFoundException;
import com.github.patbattb.filmorate.exception.LikeAlreadyExistsException;
import com.github.patbattb.filmorate.exception.NotFoundException;
import com.github.patbattb.filmorate.model.Film;
import com.github.patbattb.filmorate.service.film.FilmService;
import com.github.patbattb.filmorate.service.film.LikeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Validated
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;
    private final LikeService likeService;

    @GetMapping
    public Collection<Film> get() {
        return filmService.get();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) throws FilmNotFoundException {
        return filmService.getFilmById(id);
    }

    @PostMapping
    public Film post(@Valid @RequestBody Film film) throws FilmAlreadyExistsException {
        return filmService.post(film);
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) {
        return filmService.put(film);
    }

    @DeleteMapping("/{id}")
    public Film delete(@PathVariable int id) throws FilmNotFoundException {
        return filmService.delete(id);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Film putLike(@PathVariable int filmId, @PathVariable int userId)
            throws NotFoundException, LikeAlreadyExistsException {
        return likeService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film deleteLike(@PathVariable int filmId, @PathVariable int userId) throws NotFoundException {
        return likeService.removeLike(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopular(@RequestParam(defaultValue = "10") @Positive int count) {
        return likeService.getPopularFilms(count);
    }

}
