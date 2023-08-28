package com.github.patbattb.filmorate.controller;

import com.github.patbattb.filmorate.model.Film;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.nio.file.FileAlreadyExistsException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Value
@RestController
@RequestMapping("/films")
public class FilmController {

    @Getter(AccessLevel.NONE)
    Set<Film> films = new HashSet<>();

    @GetMapping
    Set<Film> get() {
        return films;
    }

    @PostMapping
    Film post(@Valid @RequestBody Film film) throws FileAlreadyExistsException {
        try {
            if (film == null) throw new IllegalArgumentException("The film from request cannot be null.");
            if (films.contains(film)) throw new FileAlreadyExistsException("The film is already exist.");
            films.add(film);
            log.debug("A film with id {} has been added.", film.getId());
        } catch (FileAlreadyExistsException | IllegalArgumentException e) {
            log.warn(e.getMessage());
        }
        return film;
    }

    @PutMapping
    Film put(@Valid @RequestBody Film film) {
        try {
            if (film == null) throw new IllegalArgumentException("The film from request cannot be null.");
            films.add(film);
            log.debug("A film with id {} has been putted.", film.getId());
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
        }
        return film;
    }

}
