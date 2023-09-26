package com.github.patbattb.filmorate.storage.film.like;

import lombok.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@Value
public class LikeDaoImpl implements LikeDao {

    JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Integer> getLikeListByFilmId() {
        String likeListSql = "SELECT user_id FROM likes WHERE film_id = ?;";
        return jdbcTemplate.queryForList(likeListSql, Integer.class);
    }

    @Override
    public int addLike(int filmId, int userId) {
        String likeSql = "INSERT INTO likes VALUES (?, ?);";
        return jdbcTemplate.update(likeSql, filmId, userId);
    }

    @Override
    public int removeLike(int filmId, int userId) {
        String likeSql = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        return jdbcTemplate.update(likeSql, filmId, userId);
    }

    @Override
    public Collection<Integer> getPopularFilmIds(int count) {
        String likesCountSql = """
                SELECT film_id
                FROM likes
                GROUP BY film_id
                ORDER BY COUNT(film_id) DESC
                LIMIT ?;
                """;
        return jdbcTemplate.queryForList(likesCountSql, Integer.class, count);
    }

    @Override
    public int getLikeCountByFilmId(int filmId) {
        String likeSql = "SELECT COUNT(*) FROM likes WHERE film_id = ?";
        var optionalInteger =  Optional.ofNullable(
                jdbcTemplate.queryForObject(likeSql, Integer.class, filmId));
        return optionalInteger.orElse(0);
    }
}
