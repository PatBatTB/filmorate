package com.github.patbattb.filmorate.storage.user.friend;

import java.util.Collection;

public interface FriendRequestDao {

    Collection<Integer> getFriendRequestIds(int user_id);

    int addFriendRequest(int user_id, int friend_id);

    int removeFriendRequest(int user_id, int friend_id);

}
