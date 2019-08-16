package com.endava.twittersimulation.service.impl;

import com.endava.twittersimulation.model.Tweet;
import com.endava.twittersimulation.model.User;
import com.endava.twittersimulation.repository.TweetRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TweetServiceImplTest {

    @Mock
    TweetRepository tweetRepository;

    @InjectMocks
    private TweetServiceImpl tweetService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
//        tweetService = new TweetServiceImpl(tweetRepository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSaveTweet() {
        User user = new User("sangy", "hunter2", "sang@hun.com", new ArrayList<>());
        Tweet tweet = new Tweet("TweetContent", LocalDate.now(), user);

        when(tweetRepository.save(any(Tweet.class))).thenReturn(tweet);

        Tweet createdTweet = tweetService.save(tweet);

        assertThat(createdTweet.getContent()).isSameAs(tweet.getContent());
    }

    @Test
    public void shouldUpdateTweetContent() {
        User user = new User("sangy", "hunter2", "sang@hun.com", new ArrayList<>());
        Tweet tweet = new Tweet("TweetContent", LocalDate.now(), user);

        when(tweetRepository.findById(anyLong())).thenReturn(java.util.Optional.of(tweet));
        tweetService.update(anyLong(), "Tweet Content 2");

        verify(tweetRepository, times(1)).save(tweet);
    }

    @Test
    public void shouldDeleteTweet() {
        Tweet tweet = new Tweet("TweetContent", LocalDate.now(), null);

        tweetService.delete(tweet);

        verify(tweetRepository, times(1)).delete(tweet);
    }

    @Test
    public void shouldFindTweetById() {
        User user = new User("sangy", "hunter2", "sang@hun.com", new ArrayList<>());
        Tweet tweet = new Tweet("TweetContent", LocalDate.now(), user);

        when(tweetRepository.findById(anyLong())).thenReturn(java.util.Optional.of(tweet));
        Tweet foundTweet = tweetService.findById(1L);

        assertThat(foundTweet.getContent()).isSameAs(tweet.getContent());
    }

    @Test
    public void shouldFindAllTweetsByUser() {
        User user = new User("sangy", "hunter2", "sang@hun.com", new ArrayList<>());
        Tweet tweet1 = new Tweet("TweetContent1", LocalDate.now(), user);
        Tweet tweet2 = new Tweet("TweetContent2", LocalDate.now(), user);

        List<Tweet> expectedTweets = Arrays.asList(tweet1, tweet2);

        when(tweetRepository.findAllByUserUsername(anyString())).thenReturn(expectedTweets);
        List<Tweet> foundTweets = tweetService.findAllByUser(user.getUsername(), null);

        assertThat(foundTweets.size()).isSameAs(expectedTweets.size());
    }

    @Test
    public void shouldFindAllByUserOnGivenDate() {
        User user = new User("sangy", "hunter2", "sang@hun.com", new ArrayList<>());
        Tweet tweet1 = new Tweet("TweetContent1", LocalDate.now(), user);
        Tweet tweet2 = new Tweet("TweetContent2", LocalDate.now(), user);

        List<Tweet> expectedTweets = Arrays.asList(tweet1, tweet2);

        when(tweetRepository.findAllByUserUsername(anyString())).thenReturn(expectedTweets);
        List<Tweet> foundTweets = tweetService.findAllByUser(user.getUsername(), null);

        assertThat(foundTweets.size()).isSameAs(expectedTweets.size());
    }

    @Test
    public void shouldGetAllUsersThatTweetedLastMonth() {
        LocalDate localDate = LocalDate.now().minusMonths(1);

        User user1 = new User("sangy", "hunter2", "sang@hun.com", new ArrayList<>());
        User user2 = new User("sangy", "hunter2", "sang@hun.com", new ArrayList<>());
        Tweet tweet1 = new Tweet("TweetContent1", localDate, user1);
        Tweet tweet2 = new Tweet("TweetContent2", localDate, user2);

        List<Tweet> expectedTweets = Arrays.asList(tweet1, tweet2);
        Set<User> expectedUsers = new HashSet<>();
        expectedUsers.add(user1);
        expectedUsers.add(user2);

        when(tweetRepository.findAll()).thenReturn(expectedTweets);
        Set<User> actualUsers = tweetService.getAllUsersThatTweetedLastMonth();

        assertThat(actualUsers.size()).isSameAs(expectedUsers.size());
    }

}