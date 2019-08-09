package com.endava.twittersimulation.repository;

import com.endava.twittersimulation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
