package com.endava.twittersimulation.repository;

import com.endava.twittersimulation.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findAllByUserUsername(String username);

    List<Tweet> findAllByDateOfCreationAndUserUsername(LocalDate date, String username);
}
