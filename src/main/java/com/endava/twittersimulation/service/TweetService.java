package com.endava.twittersimulation.service;

import com.endava.twittersimulation.model.Tweet;
import com.endava.twittersimulation.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TweetService {

    Tweet save(Tweet tweet);

    void update(Long tweetId, String content);

    void delete(Tweet tweet);

    Tweet findById(Long id);

    List<Tweet> findAllByUser(String username, LocalDate date);

    List<Tweet> findAllByUserOnGivenDate(String username, LocalDate date);

    Set<User> getAllUsersThatTweetedLastMonth();
}
