package com.tech.assessment.service;

import com.tech.assessment.model.User;
import java.util.List;
import java.util.UUID;

public interface UserService {
    User registerUser(User user);
    List<User> getAllUsers();
    User getUserById(UUID id);
    User updateUser(UUID id, User user);
    void deleteUser(UUID id);
}


