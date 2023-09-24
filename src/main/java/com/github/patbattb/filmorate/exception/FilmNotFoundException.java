package com.github.patbattb.filmorate.exception;

public class FilmNotFoundException extends NotFoundException{

    public FilmNotFoundException() {
        super("The film not found");
    }

    public FilmNotFoundException(String message) {
        super(message);
    }
}
