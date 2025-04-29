package com.project.Dinning.controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.Dinning.repositories.UserRepository;
import com.project.Dinning.models.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping("/")
  public User createUser(@RequestBody User user) {

    if (user.getDisplayName() == null || user.getDisplayName().isEmpty()) {
      throw new IllegalArgumentException("Display name cannot be null or empty");
    }

    if (this.userRepository.findByDisplayName(user.getDisplayName()) != null) {
      throw new IllegalArgumentException("User already exists");
    }

    return this.userRepository.save(user);

  }

}
