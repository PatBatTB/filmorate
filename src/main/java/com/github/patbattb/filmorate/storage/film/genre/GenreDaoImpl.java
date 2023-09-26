package com.github.patbattb.filmorate.storage.film.genre;

import com.github.patbattb.filmorate.model.FilmGenre;
import com.github.patbattb.filmorate.model.Genre;
import lombok.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Value
public class GenreDaoImpl implements GenreDao {

    JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Genre> getById(int id) {
        String genreSql = "SELECT * FROM genres WHERE genre_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(genreSql, this::mapGenre, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Genre> getGenreListByFilm(int filmId) {
        String genreListSql = "SELECT * FROM films_x_genres WHERE film_id = ?";
        List<FilmGenre> filmGenreList = jdbcTemplate.query(genreListSql, this::mapGenreFilms, filmId);
        return filmGenreList.stream()
                .map(filmGenre -> getById(filmGenre.genreId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    private FilmGenre mapGenreFilms(ResultSet resultSet, int numRow) throws SQLException {
        return new FilmGenre(resultSet.getInt("film_id"), resultSet.getInt("genre_id"));
    }

    private Genre mapGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return new Genre(resultSet.getInt("genre_id"), resultSet.getString("name"));
    }
}
