package com.project.Dinning.controllers;

import com.project.Dinning.models.Restaurant;
import org.springframework.web.bind.annotation.*;

import com.project.Dinning.services.RestaurantService;
import com.project.Dinning.dto.RestaurantCreateDTO;
import com.project.Dinning.enums.ReviewStatus;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("/restaurants")
@Tag(name = "Restaurant", description = "Restaurant management APIs")
public class RestaurantController {

  private final RestaurantService restaurantService;

  public RestaurantController(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

  @Operation(summary = "Create a new Restaurant", description = "Create a new Restaurant with the provided details")
  @ApiResponse(responseCode = "201", description = "Restaurant created successfully", content = @Content(schema = @Schema(implementation = Restaurant.class)))
  @ApiResponse(responseCode = "400", description = "Invalid input")
  @ApiResponse(responseCode = "409", description = "Restaurant already exists")
  @PostMapping("")
  public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody RestaurantCreateDTO restaurant) {
    Restaurant createdRestaurant = this.restaurantService.createRestaurant(restaurant);
    return new ResponseEntity<>(createdRestaurant, HttpStatus.CREATED);
  }

  @Operation(summary = "Get a Restaurant by ID", description = "Retrieve a Restaurant by its ID")
  @ApiResponse(responseCode = "200", description = "Restaurant found", content = @Content(schema = @Schema(implementation = Restaurant.class)))
  @ApiResponse(responseCode = "404", description = "Restaurant not found")
  @GetMapping("/{id}")
  public ResponseEntity<Restaurant> getRestaurantById(@PathVariable("id") Long id) {
    Restaurant restaurant = this.restaurantService.getRestaurantById(id);
    return ResponseEntity.ok(restaurant);
  }

  @Operation(summary = "Get restaurants with filtering and pagination", description = "Returns a paginated list of restaurants. Can filter by ZIP code, allergy type, and whether the restaurant has allergy scores.")
  @ApiResponse(responseCode = "200", description = "List of restaurants", content = @Content(schema = @Schema(implementation = Restaurant.class)))
  @ApiResponse(responseCode = "404", description = "No Restaurant Found")
  @GetMapping("")
  public ResponseEntity<Page<Restaurant>> getRestaurants(
      @RequestParam(required = false) String zipCode,
      @RequestParam(required = false) String allergy,
      @RequestParam(required = false) Boolean hasScore,
      @RequestParam(required = false) ReviewStatus status,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sort) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
    Page<Restaurant> restaurants = this.restaurantService.getRestaurants(zipCode, allergy, hasScore, pageable);
    return ResponseEntity.ok(restaurants);

  }

}
