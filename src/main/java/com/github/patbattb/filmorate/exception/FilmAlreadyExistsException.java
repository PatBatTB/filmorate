package com.github.patbattb.filmorate.exception;

public class FilmAlreadyExistsException extends AlreadyExistsException {

    public FilmAlreadyExistsException() {
        super("The film Already exists");
    }

    public FilmAlreadyExistsException(String message) {
        super(message);
    }
}
