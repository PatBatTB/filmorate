package com.github.patbattb.filmorate.storage.film;

import com.github.patbattb.filmorate.model.Film;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
@Component
@Slf4j
public class FilmStorageDaoImpl implements FilmStorageDao {

    JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Film> getAll() {
        String filmsSql = "SELECT * FROM films;";
        return jdbcTemplate.queryForStream(filmsSql, this::mapFilm)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Film> getById(int filmId) {
        String filmSql = "SELECT * FROM films WHERE film_id = ?;";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(filmSql, this::mapFilm, filmId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Film> getByTitleAndDate(String title, LocalDate releaseDate) {
        String filmSql = "SELECT * FROM films WHERE title = ? AND release_date = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    filmSql, this::mapFilm, title, Date.valueOf(releaseDate)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Film> add(Film film) {
        String filmSql = """
                INSERT INTO films (film_id, title, description, release_date, duration, mpaa_id)
                VALUES (?, ?, ?, ?, ?, ?);
                """;
        int returning = jdbcTemplate.update(filmSql, film.getId(), film.getTitle(), film.getDescription(),
                Date.valueOf(film.getReleaseDate()), film.getDuration().toMinutes(),
                Integer.parseInt(film.getMpaaRating()));
        return returning == 1 ? Optional.of(film) : Optional.empty();
    }

    @Override
    public Optional<Film> update(Film film) {
        String filmSql = """
                UPDATE films
                SET description = ?,
                mpaa_id = ?,
                duration = ?
                WHERE film_id = ?
                """;
        int returning = jdbcTemplate.update(filmSql, film.getDescription(), film.getMpaaRating(),
                film.getDuration().toMinutes(), film.getId());
        return returning == 1 ? Optional.of(film) : Optional.empty();
    }

    @Override
    public Optional<Film> remove(Film film) {
        String filmSql = "DELETE FROM films WHERE film_id = ?";
        int returning = jdbcTemplate.update(filmSql, film.getId());
        return returning == 1 ? Optional.of(film) : Optional.empty();
    }

    @Override
    public Optional<Integer> getMaxFilmId() {
        String sql = "SELECT MAX(film_id) FROM films";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Integer.class));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private Film mapFilm(ResultSet rs, int rowNum) throws SQLException {
        return new Film(
                rs.getInt("film_id"),
                rs.getString("title"),
                rs.getString("description"),
                new HashSet<>(),
                rs.getString("mpaa_id"),
                rs.getDate("release_date").toLocalDate(),
                Duration.ofMinutes(rs.getInt("duration"))
        );
    }
}
