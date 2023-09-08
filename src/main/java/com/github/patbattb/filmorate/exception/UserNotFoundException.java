package com.github.patbattb.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
