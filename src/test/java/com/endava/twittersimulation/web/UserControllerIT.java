package com.endava.twittersimulation.web;

import com.endava.twittersimulation.model.Tweet;
import com.endava.twittersimulation.model.User;
import com.endava.twittersimulation.model.dto.ChangePasswordDto;
import com.endava.twittersimulation.repository.TweetRepository;
import com.endava.twittersimulation.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = "application-it.yml")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT {

    private static final String BASE_URL = "/users/";
    private static final String URL_DELETE_USER = BASE_URL.concat("{username}");
    private static final String URL_CHANGE_PASSWORD = BASE_URL.concat("{username}");
    private static final String URL_DELETE_ALL_TWEETS = BASE_URL.concat("{username}/tweets");
    private static final String URL_ALL_TWEETS_FOR_USER = BASE_URL.concat("{username}/tweets");

    private MockMvc mockMvc;

    private Gson gson;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private User user;
    private String userJson;

    @Before
    public void setUp() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        user = new User("sangy", "hunter2", "sang@hun.com");
        userJson = gson.toJson(user);
    }

    @After
    public void cleanUp() {
        userRepository.deleteAll();
        tweetRepository.deleteAll();
    }

    @Test
    public void shouldCreateUser() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(userJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        User user1 = userRepository.findByUsername("sangy").get();
        assertThat(user1.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void shouldReturnStatusNoContent_whenCreatingUserThatAlreadyExists() throws Exception {
        userRepository.save(user);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(userJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        userRepository.save(user);

        mockMvc.perform(delete(URL_DELETE_USER, user.getUsername()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldChangeUserPassword() throws Exception {
        userRepository.save(user);

        ChangePasswordDto changePasswordDto = new ChangePasswordDto("hunter2", "2retnuh");
        String changePasswordDtoJson = gson.toJson(changePasswordDto);

        mockMvc.perform(patch(URL_CHANGE_PASSWORD, user.getUsername())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(changePasswordDtoJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatusNotFound_whenTryingToChangePasswordOnNonExistingUser() throws Exception {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("hunter2", "2retnuh");
        String changePasswordDtoJson = gson.toJson(changePasswordDto);

        mockMvc.perform(patch(URL_CHANGE_PASSWORD, 2L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(changePasswordDtoJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetAllTweetsForUser() throws Exception {
        userRepository.save(user);

        LocalDate date = LocalDate.now();
        Tweet tweet1 = new Tweet("TweetContent1", date, user);
        Tweet tweet2 = new Tweet("TweetContent2", date, user);
        List<Tweet> expectedTweets = Arrays.asList(tweet1, tweet2);
        tweetRepository.saveAll(expectedTweets);

        String output = mockMvc.perform(get(URL_ALL_TWEETS_FOR_USER, user.getUsername()))
                .andReturn().getResponse().getContentAsString();

        assertThat(output.contains(tweet1.getContent())).isTrue();
        assertThat(output.contains(tweet2.getContent())).isTrue();
    }

    @Test
    public void shouldDeleteAllTweetsForUser() throws Exception {
        userRepository.save(user);

        Tweet tweet1 = new Tweet("TweetContent1", LocalDate.now(), user);
        Tweet tweet2 = new Tweet("TweetContent2", LocalDate.now(), user);
        tweetRepository.saveAll(Arrays.asList(tweet1, tweet2));

        mockMvc.perform(delete(URL_DELETE_ALL_TWEETS, user.getUsername()))
                .andExpect(status().isNoContent());
    }
}