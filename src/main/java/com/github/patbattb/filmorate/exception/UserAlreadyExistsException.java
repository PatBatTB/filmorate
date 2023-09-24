package com.github.patbattb.filmorate.exception;

public class UserAlreadyExistsException extends AlreadyExistsException {

    public UserAlreadyExistsException() {
        super("The user already exist");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
