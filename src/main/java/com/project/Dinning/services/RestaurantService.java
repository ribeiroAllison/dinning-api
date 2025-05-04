package com.project.Dinning.services;

import org.springframework.stereotype.Service;

import com.project.Dinning.models.Restaurant;
import com.project.Dinning.repositories.RestaurantRepository;
import com.project.Dinning.errors.EntityNotFound;
import com.project.Dinning.errors.RestaurantAlreadyExists;
import com.project.Dinning.errors.NoResultsFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class RestaurantService {

  private final RestaurantRepository restaurantRepository;

  public RestaurantService(RestaurantRepository restaurantRepository) {
    this.restaurantRepository = restaurantRepository;

  }

  public Restaurant getRestaurantById(Long id) {
    return this.restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFound("Restaurant not found"));
  }

  public Page<Restaurant> getRestaurants(
      String zipCode,
      String allergy,
      Boolean hasScore,
      Pageable pageable) {
    Page<Restaurant> restaurantList;
    if (zipCode != null && allergy != null) {
      restaurantList = this.getRestaurantByZipCodeAndAllergy(zipCode, allergy, pageable);
    } else if (zipCode != null && hasScore) {
      restaurantList = this.getByZipCodeAndHasScore(zipCode, pageable);
    } else {
      restaurantList = this.getAllRestaurants(pageable);
    }
    if (restaurantList.isEmpty()) {
      throw new NoResultsFound("No results match your search");
    } else {
      return restaurantList;
    }

  }

  private Page<Restaurant> getRestaurantByZipCodeAndAllergy(String zipCode, String allergy, Pageable pageable) {
    Page<Restaurant> restaurants = this.restaurantRepository.findByZipCodeAndAllergy(zipCode, allergy, pageable);
    if (restaurants.isEmpty()) {
      throw new NoResultsFound("No results match your zip code " + zipCode + " and allergy " + allergy);
    }
    return restaurants;
  }

  private Page<Restaurant> getByZipCodeAndHasScore(String zipCode, Pageable pageable) {
    Page<Restaurant> restaurants = this.restaurantRepository.findByZipCodeAndScoreNotNull(zipCode, pageable);
    if (restaurants.isEmpty()) {
      throw new NoResultsFound("No results match your zip code " + zipCode + " and has score");
    }
    return restaurants;
  }

  private Page<Restaurant> getAllRestaurants(Pageable pageable) {

    Page<Restaurant> restaurants = this.restaurantRepository.findAll(pageable);
    if (restaurants.isEmpty()) {
      throw new NoResultsFound("No restaurants found");
    }
    return restaurants;

  }

  public Restaurant createRestaurant(Restaurant restaurant) {
    validateRestaurant(restaurant);
    return restaurantRepository.save(restaurant);
  }

  private void validateRestaurant(Restaurant restaurant) {
    boolean restaurantExists = this.restaurantRepository.existsByNameAndZipCode(restaurant.getName(),
        restaurant.getZipCode());
    if (restaurantExists) {
      throw new RestaurantAlreadyExists("Restaurant already exists with name '" +
          restaurant.getName() + "' and zip code '" + restaurant.getZipCode() + "'");
    }
  }

}
