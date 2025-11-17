package com.tech.assessment.service;

import com.tech.assessment.dto.UserDto;
import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto registerUser(UserDto user);
    List<UserDto> getAllUsers();
    UserDto getUserById(UUID id);
    UserDto updateUser(UUID id, UserDto user);
    void deleteUser(UUID id);
}


