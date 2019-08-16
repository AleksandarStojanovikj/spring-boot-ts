package com.endava.twittersimulation.web;

import com.endava.twittersimulation.model.Tweet;
import com.endava.twittersimulation.model.User;
import com.endava.twittersimulation.model.dto.TweetContentDto;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = "application-it.yml")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TweetControllerIT {

    private static final String BASE_URL = "/tweets/";
    private static final String URL_TWEET_BY_ID = BASE_URL.concat("{id}");
    private static final String URL_UPDATE_TWEET = BASE_URL.concat("{id}");

    private MockMvc mockMvc;

    private Gson gson;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
//        gson = new Gson();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void cleanUp() {
        tweetRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void shouldGetAllUsersThatTweetedLastMonth() throws Exception {
        User user = new User("sang", "hunter2", "h@t.com");
        userRepository.save(user);

        Tweet tweet = new Tweet("Tweet Content", LocalDate.now().minusMonths(1), user);
        tweetRepository.save(tweet);

        List<User> expectedUsers = Collections.singletonList(user);

        mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(expectedUsers)));
    }

    @Test
    public void shouldCreateTweet() throws Exception {
        User user = new User("sang", "hunter2", "h@t.com");
        userRepository.save(user);

        Tweet tweet = new Tweet("Tweet Content", null, user);

        String tweetJson = gson.toJson(tweet);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(tweetJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnBadRequest_whenNoBodyIsSentWhenCreatingTweet() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetTweetById() throws Exception {
        User user = new User("sang", "hunter2", "h@t.com");
        userRepository.save(user);

        Tweet tweet = new Tweet("Tweet Content", null, user);
        tweetRepository.save(tweet);

        String output = mockMvc.perform(get(URL_TWEET_BY_ID, tweet.getId())).andReturn().getResponse().getContentAsString();
        assertThat(output.contains(tweet.getContent())).isTrue();
    }

    @Test
    public void shouldReturnStatusNotFound_whenSearchingForNonExistingTweet() throws Exception {
        mockMvc.perform(get(URL_TWEET_BY_ID, 2L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateTweet() throws Exception {
        User user = new User("sang", "hunter2", "h@t.com", new ArrayList<>());
        userRepository.save(user);

        Tweet tweet = new Tweet("Tweet Content", null, user);
        tweetRepository.save(tweet);

        TweetContentDto content = new TweetContentDto("New Content");
        String contentJson = gson.toJson(content);

        mockMvc.perform(patch(URL_UPDATE_TWEET, tweet.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(contentJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFound_whenTryingToUpdateContentOfNonExistingTweet() throws Exception {
        TweetContentDto content = new TweetContentDto("New Content");
        String contentJson = gson.toJson(content);

        mockMvc.perform(patch(URL_TWEET_BY_ID, 2L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(contentJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}