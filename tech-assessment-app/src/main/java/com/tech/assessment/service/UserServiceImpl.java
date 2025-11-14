package com.tech.assessment.service;

import com.tech.assessment.dto.UserDto;
import com.tech.assessment.model.User;
import com.tech.assessment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = User.builder()
                .username(userDto.username())
                .password(userDto.password())
                .email(userDto.email())
                .roles(userDto.roles())
                .build();
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saved = userRepository.save(user);

        return new UserDto(
                saved.getId(),
                saved.getUsername(),
                saved.getPassword(),
                saved.getEmail(),
                saved.getRoles(),
                saved.getCreatedAt(),
                saved.getUpdatedAt());
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getRoles(),
                        user.getCreatedAt(),
                        user.getUpdatedAt()))
                .toList();
    }

    @Override
    public UserDto updateUser(UUID id, UserDto user) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (Objects.nonNull(existingUser)) {
            if (userRepository.existsByUsername(user.username()) && !existingUser.getUsername().equals(user.username())) {
                throw new RuntimeException("Username already exists");
            }
            if (userRepository.existsByEmail(user.email()) && !existingUser.getEmail().equals(user.email())) {
                throw new RuntimeException("Email already exists");
            }
            existingUser.setUsername(user.username());
            existingUser.setPassword(passwordEncoder.encode(user.password()));
            existingUser.setEmail(user.email());
            existingUser.setRoles(user.roles());
            existingUser.setUpdatedAt(LocalDateTime.now());
        }
        User saved = userRepository.save(existingUser);
        return new UserDto(
                saved.getId(),
                saved.getUsername(),
                saved.getPassword(),
                saved.getEmail(),
                saved.getRoles(),
                saved.getCreatedAt(),
                saved.getUpdatedAt());
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getUserById(UUID id) {
        return userRepository.findById(id)
                .map(user -> new UserDto(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getRoles(),
                        user.getCreatedAt(),
                        user.getUpdatedAt()
                )).orElse(null);
    }

}
