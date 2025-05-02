package com.project.Dinning.controllers;

import com.project.Dinning.models.Restaurant;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import com.project.Dinning.services.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

  private final RestaurantService restaurantService;

  public RestaurantController(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

  @PostMapping("")
  public Restaurant createRestaurant(Restaurant restaurant) {
    return this.restaurantService.createRestaurant(restaurant);
  }

  @GetMapping("/{id}")
  public Restaurant getRestaurnantById(@PathVariable("id") Long id) {
    return this.restaurantService.getRestaurantById(id);
  }

}
