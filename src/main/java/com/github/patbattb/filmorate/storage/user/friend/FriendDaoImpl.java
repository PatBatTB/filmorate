package com.github.patbattb.filmorate.storage.user.friend;

import lombok.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Value
@Component
public class FriendDaoImpl implements FriendDao {

    JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Integer> getFriendIds(int user_id) {
        String sql = "SELECT friend_id FROM friends WHERE user_id = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, user_id);
    }

    @Override
    public int addFriend(int user_id, int friend_id) {
        String sql = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
        return jdbcTemplate.update(sql, user_id, friend_id);
    }

    @Override
    public int removeFriend(int user_id, int friend_id) {
        String sql = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        return jdbcTemplate.update(sql, user_id, friend_id);
    }
}
