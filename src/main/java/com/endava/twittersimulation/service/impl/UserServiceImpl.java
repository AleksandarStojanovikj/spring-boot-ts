package com.endava.twittersimulation.service.impl;

import com.endava.twittersimulation.exceptions.UserAlreadyExistsException;
import com.endava.twittersimulation.exceptions.UserDoesNotExistException;
import com.endava.twittersimulation.model.User;
import com.endava.twittersimulation.repository.UserRepository;
import com.endava.twittersimulation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        Optional<User> existingUser = userRepository.findById(user.getId());

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        return userRepository.save(user);
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserDoesNotExistException();
        }

        existingUser.get().changePassword(oldPassword, newPassword);
    }

    @Override
    public void delete(Long userId) {

    }
}
