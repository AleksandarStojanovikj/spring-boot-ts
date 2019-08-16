package com.endava.twittersimulation.service.impl;

import com.endava.twittersimulation.model.Tweet;
import com.endava.twittersimulation.model.User;
import com.endava.twittersimulation.repository.UserRepository;
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
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    private TweetServiceImpl tweetService;

    @InjectMocks
    private UserServiceImpl userService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSaveUser() {
        User user = new User("sangy", "hunter2", "sang@hun.com", new ArrayList<>());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.save(user);

        assertThat(createdUser.getUsername()).isSameAs(user.getUsername());
    }

    @Test
    public void shouldChangeUserPassword() {
        User user = new User("sangy", "hunter2", "sang@hun.com", new ArrayList<>());
        when(userRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(user));

        String username = "sangy";
        String oldPassword = "hunter2";
        String newPassword = "2retnuh";

        userService.changePassword(username, oldPassword, newPassword);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void shouldDeleteUser() {
        User user = new User("sangy", "hunter2", "sang@hun.com", new ArrayList<>());
        when(userRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(user));

        userService.delete(user.getUsername());
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void shouldDeleteAllTweetsForUser() {
        Tweet tweet1 = new Tweet("Tweet1", LocalDate.now(), null);
        Tweet tweet2 = new Tweet("Tweet1", LocalDate.now(), null);

        User user = new User("sangy", "hunter2", "sang@hun.com", Arrays.asList(tweet1, tweet2));
        when(userRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(user));

        userService.deleteTweetsForUser(user.getUsername());
        verify(tweetService, times(1)).delete(tweet1);
        verify(tweetService, times(1)).delete(tweet2);
    }

}