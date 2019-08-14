package com.endava.twittersimulation.service.impl;

import com.endava.twittersimulation.exceptions.UserAlreadyExistsException;
import com.endava.twittersimulation.exceptions.UserDoesNotExistException;
import com.endava.twittersimulation.model.Tweet;
import com.endava.twittersimulation.model.User;
import com.endava.twittersimulation.repository.UserRepository;
import com.endava.twittersimulation.service.TweetService;
import com.endava.twittersimulation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private TweetService tweetService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TweetService tweetService) {
        this.userRepository = userRepository;
        this.tweetService = tweetService;
    }

    @Override
    public User save(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        return userRepository.save(user);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        Optional<User> existingUser = userRepository.findByUsername(username);

        if (!existingUser.isPresent()) {
            throw new UserDoesNotExistException();
        }

        existingUser.get().changePassword(oldPassword, newPassword);
        userRepository.save(existingUser.get());
    }

    @Override
    public void delete(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) {
            throw new UserDoesNotExistException();
        }

        userRepository.delete(user.get());
    }

    @Override
    public void deleteTweetsForUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) {
            throw new UserDoesNotExistException();
        }

        List<Tweet> usersTweets = user.get().getTweets();
        usersTweets.forEach(tweet -> tweetService.delete(tweet));
    }
}
