package com.endava.twittersimulation.service.impl;

import com.endava.twittersimulation.exceptions.TweetDoesNotExistException;
import com.endava.twittersimulation.model.Tweet;
import com.endava.twittersimulation.model.User;
import com.endava.twittersimulation.repository.TweetRepository;
import com.endava.twittersimulation.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TweetServiceImpl implements TweetService {

    private TweetRepository tweetRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public Tweet save(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public void update(Long tweetId, String content) {
        Optional<Tweet> existingTweet = tweetRepository.findById(tweetId);

        if (!existingTweet.isPresent()) {
            throw new TweetDoesNotExistException();
        }

        existingTweet.get().setContent(content);

        tweetRepository.save(existingTweet.get());
    }

    @Override
    public void delete(Tweet tweet) {
        tweetRepository.delete(tweet);
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
    public List<Tweet> findAllByUser(String username, LocalDate date) {
        if (date != null) {
            return findAllByUserOnGivenDate(username, date);
        }

        return tweetRepository.findAllByUserUsername(username);
    }

    @Override
    public List<Tweet> findAllByUserOnGivenDate(String username, LocalDate date) {
        return tweetRepository.findAllByDateOfCreationAndUserUsername(date, username);
    }

    @Override
    public Set<User> getAllUsersThatTweetedLastMonth() {
        Month lastMonth = LocalDateTime.now().getMonth().minus(1);

        return tweetRepository.findAll().stream()
                .filter(tweet -> lastMonth.equals(tweet.getDateOfCreation().getMonth()))
                .map(Tweet::getUser)
                .collect(Collectors.toSet());
    }
}
