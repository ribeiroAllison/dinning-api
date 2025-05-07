package com.project.Dinning.controllers;

import org.springframework.web.bind.annotation.*;
import com.project.Dinning.services.UserService;
import com.project.Dinning.dto.UserCreateDTO;
import com.project.Dinning.dto.UserUpdateDTO;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.Dinning.models.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "User management APIs")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Operation(summary = "Get a User by Display Name", description = "Retrieve a User by its Display Name")
  @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class)))
  @ApiResponse(responseCode = "404", description = "User not found")
  @GetMapping("/display-name/{displayName}")
  public ResponseEntity<User> getUserByDisplayName(@PathVariable("displayName") String displayName) {
    return ResponseEntity.ok(this.userService.getUserByDisplayName(displayName));
  }

  @Operation(summary = "Create a new User", description = "Create a new User")
  @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(schema = @Schema(implementation = User.class)))
  @ApiResponse(responseCode = "400", description = "Invalid input data")
  @PostMapping("")
  public ResponseEntity<User> createUser(@Valid @RequestBody UserCreateDTO userDTO) {
    User newUser = this.userService.createUser(userDTO);
    return new ResponseEntity<>(newUser, HttpStatus.CREATED);
  }

  @Operation(summary = "Edit an existing User", description = "Edit an existing User by its ID")
  @ApiResponse(responseCode = "200", description = "User edited successfully", content = @Content(schema = @Schema(implementation = User.class)))
  @ApiResponse(responseCode = "400", description = "Invalid input data")
  @ApiResponse(responseCode = "404", description = "User not found")
  @PutMapping("/{id}")
  public ResponseEntity<User> editUser(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateDTO user) {
    User editUser = this.userService.editUser(id, user);
    return ResponseEntity.ok(editUser);
  }
}
