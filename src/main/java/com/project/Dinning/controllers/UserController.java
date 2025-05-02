package com.project.Dinning.controllers;

import org.springframework.web.bind.annotation.*;
import com.project.Dinning.services.UserService;
import com.project.Dinning.models.User;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/display-name/{displayName}")
  public User getUserByDisplayName(@PathVariable("displayName") String displayName) {
    return this.userService.getUserByDisplayName(displayName);
  }

  @PostMapping("")
  public User createUser(@RequestBody User user) {
    return this.userService.createUser(user);
  }

  @PutMapping("/{id}")
  public User editUser(@PathVariable("id") Long id, @RequestBody User user) {
    return this.userService.editUser(id, user);
  }
}
