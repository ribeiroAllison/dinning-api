package com.project.Dinning.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.Dinning.models.Restaurant;
import com.project.Dinning.models.Review;
import com.project.Dinning.models.User;
import com.project.Dinning.repositories.ReviewRepository;
import com.project.Dinning.repositories.UserRepository;
import com.project.Dinning.repositories.RestaurantRepository;
import com.project.Dinning.errors.EntityNotFound;

@Service
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;
  private final RestaurantRepository restaurantRepository;

  public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository,
      RestaurantRepository restaurantRepository) {
    this.reviewRepository = reviewRepository;
    this.userRepository = userRepository;
    this.restaurantRepository = restaurantRepository;
  }

  public Review createReview(Review review) {
    validateNewReview(review);
    return this.reviewRepository.save(review);
  }

  private void validateNewReview(Review review) {
    Restaurant restaurantData = review.getRestaurant();
    User userData = review.getUser();

    this.restaurantRepository.findById(restaurantData.getId())
        .orElseThrow(() -> new EntityNotFound("Restaurant does not exist"));

    this.userRepository.findById(userData.getId())
        .orElseThrow(() -> new EntityNotFound("User does not exist"));

  }

}
