package com.github.patbattb.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.patbattb.filmorate.annotation.AfterThen;
import com.github.patbattb.filmorate.annotation.PositiveDuration;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Film {

    @Setter
    private int id;
    @NotBlank
    @EqualsAndHashCode.Include
    private final String title;
    @Size(max=200)
    private final String description;
    private final Set<String> genres;
    private final String mpaaRating;
    @EqualsAndHashCode.Include
    @AfterThen("1894-12-31")
    private final LocalDate releaseDate;
    @PositiveDuration
    private final Duration duration;
    private final int likeCounter;

    @JsonCreator
    public Film(String title, String description, Set<String> genres,
                String mpaaRating, LocalDate releaseDate, Duration duration) {
        this(0, title, description, genres, mpaaRating, releaseDate, duration, 0);
    }

    @Builder(toBuilder = true)
    public Film(int id, String title, String description, Set<String> genres,
                String mpaaRating, LocalDate releaseDate, Duration duration, int likeCounter) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.mpaaRating = mpaaRating;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likeCounter = likeCounter;
    }
}
