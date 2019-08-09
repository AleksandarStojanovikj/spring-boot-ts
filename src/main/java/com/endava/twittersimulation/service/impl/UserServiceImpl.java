package com.endava.twittersimulation.service.impl;

import com.endava.twittersimulation.model.User;
import com.endava.twittersimulation.repository.UserRepository;
import com.endava.twittersimulation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {

    }

    @Override
    public void delete(Long userId) {

    }
}
