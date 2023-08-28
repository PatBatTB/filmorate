package com.github.patbattb.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAlreadyExist extends Exception {
    public UserAlreadyExist(String message) {
        super(message);
        log.info(message);
    }
}
