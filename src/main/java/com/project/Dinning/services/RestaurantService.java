package com.project.Dinning.services;

import org.springframework.stereotype.Service;
import com.project.Dinning.models.Restaurant;
import com.project.Dinning.repositories.RestaurantRepository;
import com.project.Dinning.errors.EntityNotFound;
import com.project.Dinning.errors.RestaurantAlreadyExists;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {

  private final RestaurantRepository restaurantRepository;

  public RestaurantService(RestaurantRepository restaurantRepository) {
    this.restaurantRepository = restaurantRepository;

  }

  public Restaurant getRestaurantById(Long id) {
    return this.restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFound("Restaurant not found"));
  }

  public List<Restaurant> getRestaurantByZipCodeAndAllergy(String zipCode, String allergy) {
    return this.restaurantRepository.findByZipCodeAndAllergy(zipCode, allergy);
  }

  public List<Restaurant> getByZipCodeAndHasScore(String zipCode) {
    return this.restaurantRepository.findByZipCodeAndScoreNotNull(zipCode);
  }

  public List<Restaurant> getAllRestaurants() {
    List<Restaurant> restaurants = new ArrayList<>();
    restaurantRepository.findAll().forEach(restaurants::add);
    return restaurants;

  }

  public Restaurant createRestaurant(Restaurant restaurant) {
    validateRestaurant(restaurant);
    return restaurantRepository.save(restaurant);
  }

  private void validateRestaurant(Restaurant restaurant) {
    List<Restaurant> existingRestaurants = restaurantRepository.findByNameAndZipCode(
        restaurant.getName(),
        restaurant.getZipCode());

    if (!existingRestaurants.isEmpty()) {
      throw new RestaurantAlreadyExists("Restaurant already exists with the same name " + restaurant.getName()
          + "and zip-code: " + restaurant.getZipCode());
    }
  }

}
