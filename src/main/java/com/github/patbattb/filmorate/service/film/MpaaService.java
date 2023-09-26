package com.github.patbattb.filmorate.service.film;

import com.github.patbattb.filmorate.exception.NotFoundException;
import com.github.patbattb.filmorate.model.MpaaRating;
import com.github.patbattb.filmorate.storage.film.mpaa.MpaaRatingDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MpaaService {

    private final  MpaaRatingDao mpaaRatingDao;

    public MpaaRating getById(int id) throws NotFoundException {
        return mpaaRatingDao.getById(id).orElseThrow(() -> new NotFoundException("The MpaaRatingID not found"));
    }

}
