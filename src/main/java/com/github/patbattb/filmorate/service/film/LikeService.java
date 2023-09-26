package com.github.patbattb.filmorate.service.film;

import com.github.patbattb.filmorate.exception.FilmNotFoundException;
import com.github.patbattb.filmorate.exception.LikeAlreadyExistsException;
import com.github.patbattb.filmorate.exception.NotFoundException;
import com.github.patbattb.filmorate.exception.UserNotFoundException;
import com.github.patbattb.filmorate.model.Film;
import com.github.patbattb.filmorate.storage.film.FilmStorageDao;
import com.github.patbattb.filmorate.storage.film.like.LikeDao;
import com.github.patbattb.filmorate.storage.user.UserStorageDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private final LikeDao likeDao;
    private final UserStorageDao userStorageDao;
    private final FilmStorageDao filmStorageDao;

    public LikeService(LikeDao likeDao,
                       @Qualifier("userStorageDaoImpl") UserStorageDao userStorageDao,
                       @Qualifier("filmStorageDaoImpl")FilmStorageDao filmStorageDao) {
        this.likeDao = likeDao;
        this.userStorageDao = userStorageDao;
        this.filmStorageDao = filmStorageDao;
    }

    public Film addLike(int filmId, int userId) throws NotFoundException, LikeAlreadyExistsException {
        contains(filmId, userId);
        try {
            likeDao.addLike(filmId, userId);
        } catch (DuplicateKeyException e) {
            throw new LikeAlreadyExistsException();
        }
        return filmStorageDao.getById(filmId).orElseThrow(FilmNotFoundException::new);
    }

    public Film removeLike(int filmId, int userId) throws NotFoundException {
        contains(filmId, userId);
        likeDao.removeLike(filmId, userId);
        return filmStorageDao.getById(filmId).orElseThrow(FilmNotFoundException::new);
    }

    public Collection<Film> getPopularFilms(int count) {
        Collection<Integer> popularFilmIds = likeDao.getPopularFilmIds(count);
        return popularFilmIds.stream()
                .map(filmStorageDao::getById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private void contains(int filmId, int userId) throws NotFoundException {
        userStorageDao.getById(userId).orElseThrow(UserNotFoundException::new);
        filmStorageDao.getById(filmId).orElseThrow(FilmNotFoundException::new);
    }
}
