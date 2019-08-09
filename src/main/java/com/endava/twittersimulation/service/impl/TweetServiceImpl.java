package com.endava.twittersimulation.service.impl;

import com.endava.twittersimulation.model.Tweet;
import com.endava.twittersimulation.repository.TweetRepository;
import com.endava.twittersimulation.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {

    private TweetRepository tweetRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public Tweet save(Tweet tweet) {
        return null;
    }

    @Override
    public void update(Tweet tweet) {

    }

    @Override
    public void deleteAllByUser(Long userId) {

    }

    @Override
    public Tweet findById(Long id) {
        return null;
    }

    @Override
    public List<Tweet> findAllByUserId(Long userId) {
        return null;
    }

    @Override
    public List<Tweet> findAllByUserOnGivenDate(Long userId, LocalDateTime dateTime) {
        return null;
    }
}
