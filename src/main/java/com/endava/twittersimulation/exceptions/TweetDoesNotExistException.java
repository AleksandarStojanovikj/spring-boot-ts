package com.endava.twittersimulation.exceptions;

public class TweetDoesNotExistException extends RuntimeException {
    public TweetDoesNotExistException() {
        super("Tweet does not exist.");
    }
}
