package com.github.patbattb.filmorate.storage.user.friend;

import java.util.Collection;

public interface FriendDao {

    Collection<Integer> getFriendIds(int userId);

    int addFriend(int userId, int friendId);

    int removeFriend(int userId, int friendId);

}
