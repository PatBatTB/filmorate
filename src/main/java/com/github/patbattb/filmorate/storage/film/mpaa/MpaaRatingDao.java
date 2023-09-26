package com.github.patbattb.filmorate.storage.film.mpaa;

import com.github.patbattb.filmorate.model.MpaaRating;

import java.util.Optional;

public interface MpaaRatingDao {

    Optional<MpaaRating> getById(int id);
}
