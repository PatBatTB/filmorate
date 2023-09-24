package com.github.patbattb.filmorate.service.user;

import com.github.patbattb.filmorate.exception.FilmorateValidationException;
import com.github.patbattb.filmorate.exception.UserAlreadyExistsException;
import com.github.patbattb.filmorate.exception.UserNotFoundException;
import com.github.patbattb.filmorate.model.User;
import com.github.patbattb.filmorate.storage.user.friend.FriendDao;
import com.github.patbattb.filmorate.storage.user.friend.FriendRequestDao;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Value
public class FriendService {

    UserService userService;
    FriendDao friendDao;
    FriendRequestDao friendRequestDao;

    public Collection<User> getFriendsList(int userId) throws UserNotFoundException {
        var user = userService.getUserById(userId);
        var friendIds = friendDao.getFriendIds(userId);
        return friendIds.stream()
                .map(id -> {
                    try {
                        return userService.getUserById(id);
                    } catch (UserNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());
    }

    public User addFriend(int userId, int friendId)
            throws FilmorateValidationException, UserNotFoundException, UserAlreadyExistsException {
        if (userId == friendId) throw new FilmorateValidationException("The user's id cannot be same the friend's id.");
        var user = userService.getUserById(userId);
        var friend = userService.getUserById(friendId);
        if (user.getFriendList().contains(friendId)) throw new UserAlreadyExistsException(
                "The user already exists in the friends list.");
        var friendRequestIds = friendRequestDao.getFriendRequestIds(friendId);
        if (friendRequestIds.contains(userId)) {
            addEachOtherToFriends(userId, friendId);
        } else {
            addToFriendRequestList(userId, friendId);
        }
        return userService.getUserById(userId);
    }

    private void addEachOtherToFriends(int userId, int friendId) {
        friendDao.addFriend(userId, friendId);
        friendDao.addFriend(friendId, userId);
        friendRequestDao.removeFriendRequest(friendId, userId);
    }

    private void addToFriendRequestList(int userId, int friendId) {
        friendRequestDao.addFriendRequest(userId, friendId);
    }

    public User removeFriend(int userId, int friendId)
            throws FilmorateValidationException, UserNotFoundException{
        if (userId == friendId) throw new FilmorateValidationException("The user's id cannot be same the friend's id.");
        friendDao.removeFriend(userId, friendId);
        friendDao.removeFriend(friendId, userId);
        friendRequestDao.removeFriendRequest(userId, friendId);
        return userService.getUserById(userId);
    }

    public Collection<User> getCommonFriends(int userId, int otherId)
            throws FilmorateValidationException, UserNotFoundException{
        if (userId == otherId) throw new FilmorateValidationException("The users' ids cannot be same.");
        var friendList = friendDao.getFriendIds(userId);
        friendList.retainAll(friendDao.getFriendIds(otherId));
        return friendList.stream()
                .map(id -> {
                    try {
                        return userService.getUserById(id);
                    } catch (UserNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());
    }
}
