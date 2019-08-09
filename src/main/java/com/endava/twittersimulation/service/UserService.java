package com.endava.twittersimulation.service;

import com.endava.twittersimulation.model.User;

public interface UserService {
    User save(User user);

    void changePassword(Long userId, String oldPassword, String newPassword);

    void delete(Long userId);
}
