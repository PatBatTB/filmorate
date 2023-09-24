package com.github.patbattb.filmorate.storage.user.friend;

import lombok.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Value
public class FriendRequestDaoImpl implements FriendRequestDao {

    JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Integer> getFriendRequestIds(int user_id) {
        String sql = "SELECT friend_id FROM friend_requests WHERE user_id = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, user_id);
    }

    @Override
    public int addFriendRequest(int user_id, int friend_id) {
        String sql = "INSERT INTO friend_requests (user_id, friend_id) VALUES (?, ?)";
        return jdbcTemplate.update(sql, user_id, friend_id);
    }

    @Override
    public int removeFriendRequest(int user_id, int friend_id) {
        String sql = "DELETE FROM friend_requests WHERE user_id = ? AND friend_id = ?";
        return jdbcTemplate.update(sql, user_id, friend_id);
    }
}
