package com.github.patbattb.filmorate.storage.film.mpaa;

import com.github.patbattb.filmorate.model.MpaaRating;
import lombok.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
@Value
public class MpaaRatingDaoImpl implements MpaaRatingDao {

    JdbcTemplate jdbcTemplate;

    @Override
    public Optional<MpaaRating> getById(int id) {
        String mpaaSql = "SELECT * FROM mpaa_ratings WHERE mpaa_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(mpaaSql, this::mapMpaa,id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    private MpaaRating mapMpaa(ResultSet rs, int RowNum) throws SQLException {
        return new MpaaRating(rs.getInt("mpaa_id"), rs.getString("name"));
    }
}
