package com.project.Dinning.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.project.Dinning.models.Restaurant;
import com.project.Dinning.dto.ReviewDTO;
import com.project.Dinning.models.Review;
import com.project.Dinning.models.User;
import com.project.Dinning.repositories.ReviewRepository;
import com.project.Dinning.repositories.UserRepository;
import com.project.Dinning.repositories.RestaurantRepository;
import com.project.Dinning.errors.EntityNotFound;
import com.project.Dinning.enums.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

  public Review createReviewFromDTO(ReviewDTO reviewDTO) {
    User user = userRepository.findById(reviewDTO.getUserId())
        .orElseThrow(() -> new EntityNotFound("User not found with ID: " + reviewDTO.getUserId()));

    Restaurant restaurant = restaurantRepository.findById(reviewDTO.getRestaurantId())
        .orElseThrow(() -> new EntityNotFound("Restaurant not found with ID: " + reviewDTO.getRestaurantId()));
    Review review = new Review();
    review.setUser(user);
    review.setRestaurant(restaurant);
    review.setPeanutScore(reviewDTO.getPeanutScore());
    review.setEggScore(reviewDTO.getEggScore());
    review.setDairyScore(reviewDTO.getDairyScore());
    review.setCommentary(reviewDTO.getCommentary());
    review.setStatus(ReviewStatus.PENDING);

    return this.reviewRepository.save(review);
  }

  public Page<Review> getReviews(ReviewStatus status, Pageable pageable) {
    if (status != null) {
      return this.getReviewByStatus(status, pageable);
    } else {
      return this.getAllReviews(pageable);
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

  public Page<Review> getByApprovedStatusAndId(Long id, Pageable pageable) {
    Page<Review> reviews = this.getReviewByStatusAndRestaurantId(ReviewStatus.APPROVED, id, pageable);
    if (reviews.isEmpty()) {
      throw new EntityNotFound("No approved reviews found for restaurant with id " + id);
    }

    return reviews;
  }

  private Page<Review> getReviewByStatusAndRestaurantId(ReviewStatus status, Long restaurantId, Pageable pageable) {
    Page<Review> reviews = this.reviewRepository.findByStatusAndRestaurant_Id(status, restaurantId, pageable);
    if (reviews.isEmpty()) {
      throw new EntityNotFound(
          "No reviews found with status " + status.getDisplayName() + " for restaurant with id " + restaurantId);
    }
    return reviews;
  }

  private Page<Review> getReviewByStatus(ReviewStatus status, Pageable pageable) {
    Page<Review> reviews = this.reviewRepository.findByStatus(status, pageable);
    if (reviews.isEmpty()) {
      throw new EntityNotFound("No reviews found with status " + status.getDisplayName());
    }
    return reviews;
  }

  private Page<Review> getAllReviews(Pageable pageable) {

    Page<Review> reviews = this.reviewRepository.findAll(pageable);
    if (reviews.isEmpty()) {
      throw new EntityNotFound("No reviews found");
    }
    return reviews;

  }

}
