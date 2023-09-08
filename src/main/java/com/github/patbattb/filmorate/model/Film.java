package com.github.patbattb.filmorate.model;

import com.github.patbattb.filmorate.annotation.AfterThen;
import com.github.patbattb.filmorate.annotation.PositiveDuration;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Film {

    @Setter
    int id;
    @NotBlank
    @EqualsAndHashCode.Include
    private final String title;
    @Size(max=200)
    private final String description;
    @EqualsAndHashCode.Include
    @AfterThen("1894-12-31")
    private final LocalDate releaseDate;
    @PositiveDuration
    private final Duration duration;
}
