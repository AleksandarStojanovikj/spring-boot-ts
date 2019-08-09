package com.endava.twittersimulation.service;

import com.endava.twittersimulation.model.Tweet;

import java.time.LocalDateTime;
import java.util.List;

public interface TweetService {

    Tweet save(Tweet tweet);

    void update(Tweet tweet);

    void deleteAllByUser(Long userId);

    Tweet findById(Long id);

    List<Tweet> findAllByUserId(Long userId);

    List<Tweet> findAllByUserOnGivenDate(Long userId, LocalDateTime dateTime);
}
