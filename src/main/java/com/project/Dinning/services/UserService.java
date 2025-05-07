package com.project.Dinning.services;

import org.springframework.stereotype.Service;
import com.project.Dinning.repositories.UserRepository;
import com.project.Dinning.models.User;
import com.project.Dinning.errors.EntityNotFound;
import com.project.Dinning.dto.UserCreateDTO;
import com.project.Dinning.dto.UserUpdateDTO;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUserByDisplayName(String displayName) {
    User foundUser = this.userRepository.findByDisplayNameIgnoreCase(displayName);
    if (foundUser == null) {
      throw new EntityNotFound("User not found with this display name " + displayName);
    }
    return foundUser;
  }

  public User createUser(UserCreateDTO dtoUser) {
    validateNewUser(dtoUser);
    User newUser = new User();
    newUser.setDisplayName(dtoUser.getDisplayName());
    newUser.setCity(dtoUser.getCity());
    newUser.setZipCode(dtoUser.getZipCode());
    newUser.setPeanutAllergy(dtoUser.isPeanutAllergy());
    newUser.setEggAllergy(dtoUser.isEggAllergy());
    newUser.setDairyAllergy(dtoUser.isDairyAllergy());
    return userRepository.save(newUser);
  }

  public User editUser(Long id, UserUpdateDTO dtoUser) {
    User existingUser = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFound("User not found"));

    if (dtoUser.getCity() != null)
      existingUser.setCity(dtoUser.getCity());
    if (dtoUser.getZipCode() != null)
      existingUser.setZipCode(dtoUser.getZipCode());
    if (dtoUser.getPeanutAllergy() != null)
      existingUser.setPeanutAllergy(dtoUser.getPeanutAllergy());
    if (dtoUser.getEggAllergy() != null)
      existingUser.setEggAllergy(dtoUser.getEggAllergy());
    if (dtoUser.getDairyAllergy() != null)
      existingUser.setDairyAllergy(dtoUser.getDairyAllergy());
    return userRepository.save(existingUser);
  }

  private void validateNewUser(UserCreateDTO user) {
    if (user.getDisplayName() == null || user.getDisplayName().isEmpty()) {
      throw new IllegalArgumentException("Display name cannot be null or empty");
    }

    if (userRepository.findByDisplayNameIgnoreCase(user.getDisplayName()) != null) {
      throw new IllegalArgumentException("User already exists");
    }
  }

}
