package com.endava.twittersimulation.repository;

import com.endava.twittersimulation.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
