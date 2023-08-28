package com.github.patbattb.filmorate.model;

import com.github.patbattb.filmorate.annotation.AfterThen;
import com.github.patbattb.filmorate.annotation.PositiveDuration;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.Duration;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Film {

    private static int currentId = 0;

    int id = ++currentId;
    @NotBlank
    @EqualsAndHashCode.Include
    String title;
    @Size(max=200)
    String description;
    @EqualsAndHashCode.Include
    @AfterThen("1894-12-31")
    LocalDate releaseDate;
    @PositiveDuration
    Duration duration;
}
