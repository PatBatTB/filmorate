package com.github.patbattb.filmorate.storage.user;

import com.github.patbattb.filmorate.model.User;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Value
@Slf4j
public class UserStorageDaoImpl implements UserStorageDao {

    JdbcTemplate jdbcTemplate;

    @Override
    public Collection<User> getAll() {
        String userIdsSql = "SELECT user_id FROM users";
        return jdbcTemplate.queryForList(userIdsSql, Integer.class).stream()
                .map(this::getById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<User> getById(int userId) {
        String userSql = """
                SELECT u.*, f.friend_id AS friend_id, fr.friend_id AS friend_request_id
                FROM users u
                LEFT JOIN friends f USING (user_id)
                LEFT JOIN friend_requests fr USING (user_id)
                WHERE u.user_id = ?;
                """;
        try {
            return Optional.ofNullable(jdbcTemplate.query(userSql, this::userExtractor , userId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getByEmail(String email) {
        String userSql = """
                SELECT u.*, f.friend_id AS friend_id, fr.friend_id AS friend_request_id
                FROM users u
                LEFT JOIN friends f USING (user_id)
                LEFT JOIN friend_requests fr USING (user_id)
                WHERE u.email = ?;
                """;
        try {
            return Optional.ofNullable(jdbcTemplate.query(userSql, this::userExtractor , email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> add(User user) {
        String sql = """
        INSERT INTO users (user_id, email, login, nickname, birth_date)
        VALUES (?, ?, ?, ?, ?);
        """;
        int returning = jdbcTemplate.update(sql, user.getId(), user.getEmail(),
                    user.getLogin(), user.getNickname(), Date.valueOf(user.getBirth_date()));
        return returning == 1 ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> update(User user) {
        String sql = """
                UPDATE users
                SET login = ?,
                nickname = ?,
                BIRTH_DATE = ?
                WHERE user_id = ?;
                """;

        int returning = jdbcTemplate.update(sql, user.getLogin(), user.getNickname(),
                Date.valueOf(user.getBirth_date()), user.getId());
        return returning == 1 ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> remove(User user) {
        String userSql = "DELETE FROM users WHERE user_id = ?";
        int returning = jdbcTemplate.update(userSql, user.getId());
        return returning == 1 ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<Integer> getMaxUserId() {
        String sql = "SELECT MAX(user_id) FROM users";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Integer.class));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private User userExtractor (ResultSet rs) throws SQLException {
        Optional<User> optionalUser = Optional.empty();
        if (rs.next()) {
            var date = rs.getDate("birth_date");
            var user = new User(
                    rs.getInt("user_id"),
                    rs.getString("email"),
                    rs.getString("login"),
                    rs.getString("nickname"),
                    date == null ? null : date.toLocalDate(),
                    new HashSet<>(),
                    new HashSet<>()
            );
            user.getFriendList().add(rs.getInt("friend_id"));
            user.getFriendRequestList().add(rs.getInt("friend_request_id"));
            optionalUser = Optional.of(user);
        }
        if (optionalUser.isPresent()) {
            while (rs.next()) {
                optionalUser.get().getFriendList().add(rs.getInt("friend_id"));
                optionalUser.get().getFriendRequestList().add(rs.getInt("friend_request_id"));
            }
            optionalUser.get().getFriendList().remove(0);
            optionalUser.get().getFriendRequestList().remove(0);
        }

        return optionalUser.orElseThrow(() -> new EmptyResultDataAccessException(1));

    }
}
