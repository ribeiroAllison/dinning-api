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
import java.util.ArrayList;

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

  public List<Review> getReviews(ReviewStatus status) {
    if (status != null) {
      return this.getReviewByStatus(status);
    } else {
      return this.getAllReviews();
    }
  }

  public Review getReviewById(Long id) {
    return this.reviewRepository.findById(id)
        .orElseThrow(() -> new EntityNotFound("Review not found with id " + id));
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

  public void changeStatusToApproved(Long id) {
    Optional<Review> reviewToUpdate = this.reviewRepository.findById(id);
    if (reviewToUpdate.isPresent()) {
      Review review = reviewToUpdate.get();
      review.setStatus(ReviewStatus.APPROVED);
      this.reviewRepository.save(review);
    } else {
      throw new EntityNotFound("Review not found");
    }
  }

  public void changeStatusToRejected(Long id) {
    Optional<Review> reviewToUpdate = this.reviewRepository.findById(id);
    if (reviewToUpdate.isPresent()) {
      Review review = reviewToUpdate.get();
      review.setStatus(ReviewStatus.REJECTED);
      this.reviewRepository.save(review);
    } else {
      throw new EntityNotFound("Review not found");
    }
  }

  public List<Review> getByApprovedStatusAndId(Long id) {
    List<Review> reviews = this.getReviewByStatusAndRestaurantId(ReviewStatus.APPROVED, id);
    if (reviews.isEmpty()) {
      throw new EntityNotFound("No approved reviews found for restaurant with id " + id);
    }

    return reviews;
  }

  private List<Review> getReviewByStatusAndRestaurantId(ReviewStatus status, Long restaurantId) {
    return this.reviewRepository.findByStatusAndRestaurant_Id(status, restaurantId);
  }

  private void validateNewReview(Review review) {
    Restaurant restaurantData = review.getRestaurant();
    User userData = review.getUser();

    this.restaurantRepository.findById(restaurantData.getId())
        .orElseThrow(() -> new EntityNotFound("Restaurant does not exist"));

    this.userRepository.findById(userData.getId())
        .orElseThrow(() -> new EntityNotFound("User does not exist"));

  }

  private List<Review> getReviewByStatus(ReviewStatus status) {
    List<Review> reviews = this.reviewRepository.findByStatus(status);
    if (reviews.isEmpty()) {
      throw new EntityNotFound("No reviews found with status " + status.getDisplayName());
    }
    return reviews;
  }

  private List<Review> getAllReviews() {
    List<Review> reviews = new ArrayList<>();
    this.reviewRepository.findAll().forEach(reviews::add);
    if (reviews.isEmpty()) {
      throw new EntityNotFound("No reviews found");
    }
    return reviews;

  }

}
