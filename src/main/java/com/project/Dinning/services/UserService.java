package com.project.Dinning.services;

import org.springframework.stereotype.Service;
import com.project.Dinning.repositories.UserRepository;
import com.project.Dinning.models.User;
import com.project.Dinning.errors.EntityNotFound;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUserByDisplayName(String displayName) {
    return userRepository.findByDisplayName(displayName);
  }

  public User createUser(User user) {
    validateNewUser(user);
    return userRepository.save(user);
  }

  public User editUser(Long id, User user) {
    User existingUser = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFound("User not found"));

    validateUserUpdate(existingUser, user);
    return userRepository.save(user);
  }

  private void validateNewUser(User user) {
    if (user.getDisplayName() == null || user.getDisplayName().isEmpty()) {
      throw new IllegalArgumentException("Display name cannot be null or empty");
    }

    if (userRepository.findByDisplayName(user.getDisplayName()) != null) {
      throw new IllegalArgumentException("User already exists");
    }
  }

  private void validateUserUpdate(User existingUser, User updatedUser) {
    if (!updatedUser.getDisplayName().equalsIgnoreCase(existingUser.getDisplayName())) {
      throw new IllegalArgumentException("User display name cannot be changed");
    }
  }
}
