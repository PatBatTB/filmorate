package com.github.patbattb.filmorate.storage.film.like;

import java.util.Collection;

public interface LikeDao {

    Collection<Integer> getLikeListByFilmId();

    int addLike(int filmId, int userId);

    int removeLike(int filmId, int userId);

    Collection<Integer> getPopularFilmIds(int count);

    int getLikeCountByFilmId(int filmId);
}
