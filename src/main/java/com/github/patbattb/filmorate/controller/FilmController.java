package com.github.patbattb.filmorate.controller;

import com.github.patbattb.filmorate.model.Film;
import com.github.patbattb.filmorate.service.FilmService;
import jakarta.validation.Valid;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Value
@RestController
@RequestMapping("/films")
public class FilmController {

    FilmService filmService;

    @GetMapping
    public Collection<Film> get() {
        return filmService.get();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        return filmService.getFilmById(id);
    }

    @PostMapping
    public Film post(@Valid @RequestBody Film film) {
        return filmService.post(film);
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) {
        return filmService.put(film);
    }

}
