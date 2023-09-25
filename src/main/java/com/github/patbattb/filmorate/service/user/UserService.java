package com.github.patbattb.filmorate.service.user;

import com.github.patbattb.filmorate.exception.UserAlreadyExistsException;
import com.github.patbattb.filmorate.exception.UserNotFoundException;
import com.github.patbattb.filmorate.model.User;
import com.github.patbattb.filmorate.storage.user.UserStorageDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserService {

    UserStorageDao userStorageDao;

    public UserService(@Qualifier("userStorageDaoImpl") UserStorageDao userStorageDao) {
        this.userStorageDao = userStorageDao;
    }

    public Collection<User> getAll() {
        return userStorageDao.getAll();
    }

    public User getUserById(int id) throws UserNotFoundException {
        return userStorageDao.getById(id).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        return userStorageDao.getByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public User post(User user) throws UserAlreadyExistsException {
        if (userStorageDao.getByEmail(user.getEmail()).isPresent()) throw new UserAlreadyExistsException();
        user.setId(getNextId());
        user = userStorageDao.add(user).orElseThrow(RuntimeException::new);
        log.debug("A user with id {} has been added.", user.getId());
        return user;
    }

    public User put(User user) {
        var optionalUser = userStorageDao.getByEmail(user.getEmail());
        if (optionalUser.isEmpty()) {
            user.setId(getNextId());
            user = userStorageDao.add(user).orElseThrow(RuntimeException::new);
        } else {
            var oldUser = optionalUser.get();
            user = user.toBuilder()
                    .id(oldUser.getId())
                    .friendList(oldUser.getFriendList())
                    .friendRequestList(oldUser.getFriendRequestList())
                    .build();
            user = userStorageDao.update(user).orElseThrow(RuntimeException::new);
        }
        log.debug("A user with id {} has been putted.", user.getId());
        return user;
    }

    public User deleteUserById(int id) throws UserNotFoundException {
        var optionalUser = userStorageDao.getById(id);
        optionalUser.ifPresent(userStorageDao::remove);
        return optionalUser.orElseThrow(UserNotFoundException::new);
    }

    private int getNextId() {
        int currentMaxId = userStorageDao.getMaxUserId().orElse(0);
        return ++currentMaxId;
    }
}
