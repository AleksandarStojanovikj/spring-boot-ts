package com.endava.twittersimulation.web;

import com.endava.twittersimulation.model.Tweet;
import com.endava.twittersimulation.model.User;
import com.endava.twittersimulation.model.dto.ChangePasswordDto;
import com.endava.twittersimulation.service.TweetService;
import com.endava.twittersimulation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private UserService userService;
    private TweetService tweetService;

    @Autowired
    public UserController(UserService userService, TweetService tweetService) {
        this.userService = userService;
        this.tweetService = tweetService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User savedUser = userService.save(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity deleteUser(@PathVariable String username) {
        userService.delete(username);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/{username}")
    public ResponseEntity<?> changePassword(@PathVariable String username,
                                            @RequestBody ChangePasswordDto changePasswordDto) {
        try {
            userService.changePassword(username, changePasswordDto.getOldPassword(), changePasswordDto.getNewPassword());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{username}/tweets")
    public ResponseEntity<List<Tweet>> getAllTweetsForUser(@PathVariable String username,
                                                           @RequestParam(name = "date", required = false)
                                                           @DateTimeFormat(pattern = "yyyy.MM.dd") LocalDate date) {

        List<Tweet> userTweets = tweetService.findAllByUser(username, date);
        return new ResponseEntity<>(userTweets, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{username}/tweets")
    public ResponseEntity deleteAllTweetsByUser(@PathVariable String username) {
        userService.deleteTweetsForUser(username);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
