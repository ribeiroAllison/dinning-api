package com.project.Dinning.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.project.Dinning.models.Restaurant;
import com.project.Dinning.dto.ReviewDTO;
import com.project.Dinning.dto.ReviewResponseDTO;
import com.project.Dinning.models.Review;
import com.project.Dinning.mappers.ReviewMapper;
import com.project.Dinning.models.User;
import com.project.Dinning.repositories.ReviewRepository;
import com.project.Dinning.repositories.UserRepository;
import com.project.Dinning.repositories.RestaurantRepository;
import com.project.Dinning.errors.EntityNotFound;
import com.project.Dinning.enums.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Objects;

@Service
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;
  private final RestaurantRepository restaurantRepository;
  private final ReviewMapper reviewMapper;

  public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository,
      RestaurantRepository restaurantRepository, ReviewMapper reviewMapper) {
    this.reviewRepository = reviewRepository;
    this.userRepository = userRepository;
    this.restaurantRepository = restaurantRepository;
    this.reviewMapper = reviewMapper;
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

  public Page<ReviewResponseDTO> getReviewsDTO(ReviewStatus status, Pageable pageable) {
    Page<Review> reviews;
    if (status != null) {
      reviews = this.getReviewByStatus(status, pageable);
    } else {
      reviews = this.getAllReviews(pageable);
    }

    return reviewMapper.toDTOPage(reviews);
  }

  public ReviewResponseDTO getReviewDTOById(Long id) {
    Review review = this.reviewRepository.findById(id)
        .orElseThrow(() -> new EntityNotFound("Review not found with id " + id));

    return reviewMapper.toDTO(review);
  }

  public void updateReviewStatus(Long id, ReviewStatus status) {
    Optional<Review> reviewToUpdate = this.reviewRepository.findById(id);
    if (reviewToUpdate.isPresent()) {
      Review review = reviewToUpdate.get();
      review.setStatus(status);
      this.reviewRepository.save(review);
      this.updateRestaurantScore(review.getRestaurant().getId());

    } else {
      throw new EntityNotFound("Review not found");
    }
  }

  public Page<ReviewResponseDTO> getByApprovedStatusAndId(Long id, Pageable pageable) {
    Page<Review> reviews = this.getReviewByStatusAndRestaurantId(ReviewStatus.APPROVED, id, pageable);
    if (reviews.isEmpty()) {
      throw new EntityNotFound("No approved reviews found for restaurant with id " + id);
    }

    return reviewMapper.toDTOPage(reviews);
  }

  private void updateRestaurantScore(Long restaurnantId) {
    Restaurant restaurant = this.restaurantRepository.findById(restaurnantId)
        .orElseThrow(() -> new EntityNotFound("Restaurant not found with id " + restaurnantId));
    Double originalEggScore = restaurant.getEggScore();
    Double originalDairyScore = restaurant.getDairyScore();
    Double originalPeanutScore = restaurant.getPeanutScore();
    List<Review> approvedReviews = this.reviewRepository.findApprovedReviewsByRestaurant(restaurnantId);

    double avgEggScore = approvedReviews.stream()
        .filter(review -> review.getEggScore() != null)
        .mapToInt(Review::getEggScore)
        .average()
        .orElse(0.0);

    double avgDairyScore = approvedReviews.stream()
        .filter(review -> review.getDairyScore() != null)
        .mapToInt(Review::getDairyScore)
        .average()
        .orElse(0.0);

    double avgPeanutScore = approvedReviews.stream()
        .filter(review -> review.getPeanutScore() != null)
        .mapToInt(Review::getPeanutScore)
        .average()
        .orElse(0.0);

    restaurant.setEggScore(avgEggScore > 0.0 ? avgEggScore : null);
    restaurant.setDairyScore(avgDairyScore > 0.0 ? avgDairyScore : null);
    restaurant.setPeanutScore(avgPeanutScore > 0.0 ? avgPeanutScore : null);

    if (!Objects.equals(restaurant.getEggScore(), originalEggScore)
        || !Objects.equals(restaurant.getDairyScore(), originalDairyScore)
        || !Objects.equals(restaurant.getPeanutScore(), originalPeanutScore)) {
      this.restaurantRepository.save(restaurant);
    }

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
