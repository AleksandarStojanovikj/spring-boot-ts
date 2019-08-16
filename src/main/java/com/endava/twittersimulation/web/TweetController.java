package com.endava.twittersimulation.web;

import com.endava.twittersimulation.exceptions.UserDoesNotExistException;
import com.endava.twittersimulation.model.Tweet;
import com.endava.twittersimulation.model.User;
import com.endava.twittersimulation.model.dto.TweetContentDto;
import com.endava.twittersimulation.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/tweets")
public class TweetController {

    private TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<Set<User>> getAllUsersThatTweetedLastMonth() {
        Set<User> usersThatTweetedLastMonth = tweetService.getAllUsersThatTweetedLastMonth();
        return new ResponseEntity<>(usersThatTweetedLastMonth, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createTweet(@RequestBody Tweet tweet) {
        try {
            Tweet savedTweet = tweetService.save(tweet);
            return new ResponseEntity<>(savedTweet, HttpStatus.CREATED);
        } catch (UserDoesNotExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTweetById(@PathVariable Long id) {
        try {
            Tweet tweet = tweetService.findById(id);
            return new ResponseEntity<>(tweet, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> updateTweet(@PathVariable Long id, @RequestBody TweetContentDto content) {
        try {
            tweetService.update(id, content.getContent());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
