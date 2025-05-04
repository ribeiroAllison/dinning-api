package com.project.Dinning.controllers;

import com.project.Dinning.models.Restaurant;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import com.project.Dinning.services.RestaurantService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

  private final RestaurantService restaurantService;

  public RestaurantController(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

  @PostMapping("")
  public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant) {
    Restaurant createdRestaurant = this.restaurantService.createRestaurant(restaurant);
    return new ResponseEntity<>(createdRestaurant, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Restaurant> getRestaurantById(@PathVariable("id") Long id) {
    Restaurant restaurant = this.restaurantService.getRestaurantById(id);
    return ResponseEntity.ok(restaurant);
  }

  @GetMapping("")
  public ResponseEntity<Page<Restaurant>> getRestaurants(
      @RequestParam(required = false) String zipCode,
      @RequestParam(required = false) String allergy,
      @RequestParam(required = false) Boolean hasScore,
      Pageable pageable) {

    Page<Restaurant> restaurants = this.restaurantService.getRestaurants(zipCode, allergy, hasScore, pageable);
    return ResponseEntity.ok(restaurants);

  }

}
