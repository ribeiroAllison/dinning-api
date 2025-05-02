package com.project.Dinning.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.Dinning.models.Restaurant;
import com.project.Dinning.models.Review;
import com.project.Dinning.models.User;
import com.project.Dinning.repositories.ReviewRepository;
import com.project.Dinning.repositories.UserRepository;
import com.project.Dinning.repositories.RestaurantRepository;
import com.project.Dinning.errors.EntityNotFound;
import com.project.Dinning.enums.ReviewStatus;

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

  public List<Review> getReviewByStatus(ReviewStatus status) {
    return this.reviewRepository.findByStatus(status);
  }

  public Iterable<Review> getAllReviews() {
    return this.reviewRepository.findAll();
  }

  public Optional<Review> getReviewById(Long id) {
    return this.reviewRepository.findById(id);
  }

  public List<Review> getReviewByStatusAndRestaurantId(ReviewStatus status, Long restaurantId) {
    return this.reviewRepository.findByStatusAndRestaurant_Id(status, restaurantId);
  }

  public void updateReviewStatus(Long id, ReviewStatus status) {
    Optional<Review> reviewToUpdate = this.reviewRepository.findById(id);
    if (reviewToUpdate.isPresent()) {
      Review review = reviewToUpdate.get();
      review.setStatus(status);
      this.reviewRepository.save(review);
    } else {
      throw new EntityNotFound("Review not found");
    }
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
