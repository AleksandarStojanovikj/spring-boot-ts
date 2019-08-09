package com.endava.twittersimulation.service.impl;

import com.endava.twittersimulation.exceptions.TweetAlreadyExistsException;
import com.endava.twittersimulation.exceptions.TweetDoesNotExistException;
import com.endava.twittersimulation.model.Tweet;
import com.endava.twittersimulation.repository.TweetRepository;
import com.endava.twittersimulation.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TweetServiceImpl implements TweetService {

    private TweetRepository tweetRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public Tweet save(Tweet tweet) {
        Optional<Tweet> existingTweet = tweetRepository.findById(tweet.getId());

        if (existingTweet.isPresent()) {
            throw new TweetAlreadyExistsException();
        }

        return tweetRepository.save(tweet);
    }

    @Override
    public void update(Tweet tweet) {
        Optional<Tweet> existingTweet = tweetRepository.findById(tweet.getId());

        if (!existingTweet.isPresent()) {
            throw new TweetDoesNotExistException();
        }

        existingTweet.get().setContent(tweet.getContent());

        tweetRepository.save(existingTweet.get());
    }

    @Override
    public void deleteAllByUser(Long userId) {
        tweetRepository.deleteAllByUserId(userId);
    }

    @Override
    public Tweet findById(Long id) {
        Optional<Tweet> tweet = tweetRepository.findById(id);

        if (!tweet.isPresent()) {
            throw new TweetDoesNotExistException();
        }

        return tweet.get();
    }

    @Override
    public List<Tweet> findAllByUserId(Long userId) {
        return tweetRepository.findAllByUserId(userId);
    }

    @Override
    public List<Tweet> findAllByUserOnGivenDate(Long userId, LocalDateTime dateTime) {
        return null;
    }
}
