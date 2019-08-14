package com.endava.twittersimulation.service;

import com.endava.twittersimulation.model.User;

public interface UserService {
    User save(User user);

    void changePassword(String username, String oldPassword, String newPassword);

    void delete(String username);

    void deleteTweetsForUser(String username);
}
