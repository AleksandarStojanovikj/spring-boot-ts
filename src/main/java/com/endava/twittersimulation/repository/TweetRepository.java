package com.endava.twittersimulation.repository;

import com.endava.twittersimulation.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    void deleteAllByUserId(Long userId);

    List<Tweet> findAllByUserId(Long userId);
}
