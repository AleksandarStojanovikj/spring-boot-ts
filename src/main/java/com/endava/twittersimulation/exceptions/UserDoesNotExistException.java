package com.endava.twittersimulation.exceptions;

public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException() {
        super("User does not exist.");
    }
}
