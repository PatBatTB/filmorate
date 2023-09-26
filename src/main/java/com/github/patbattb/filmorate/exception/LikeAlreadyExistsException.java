package com.github.patbattb.filmorate.exception;

public class LikeAlreadyExistsException extends AlreadyExistsException {

    public LikeAlreadyExistsException() {
        super("This user has already liked this movie.");
    }

    public LikeAlreadyExistsException(String message) {
        super(message);
    }
}
