package com.project.Dinning.errors;

public class RestaurantAlreadyExists extends RuntimeException {
  public RestaurantAlreadyExists(String message) {
    super(message);
  }
}
