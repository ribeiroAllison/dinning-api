package com.project.Dinning.controllers;

import org.springframework.web.bind.annotation.*;

import com.project.Dinning.enums.ReviewStatus;
import com.project.Dinning.errors.EntityNotFound;
import com.project.Dinning.models.Review;
import com.project.Dinning.services.ReviewService;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

  private final ReviewService reviewService;

  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @PostMapping("")
  public Review createReview(@RequestBody Review review) {
    return this.reviewService.createReview(review);
  }

  @PutMapping("/admin/{id}/{status}")
  public String updateReviewStatus(@PathVariable("id") Long id, @PathVariable("status") ReviewStatus status) {
    try {
      this.reviewService.updateReviewStatus(id, status);
      return "Review status updated successfully";
    } catch (Exception e) {
      return "Error updating review status: " + e.getMessage();
    }
  }

  @GetMapping("")
  public List<Review> getReviews(@RequestParam(required = false) ReviewStatus status) {
    if (status != null) {
      return this.reviewService.getReviewByStatus(status);
    } else {
      List<Review> reviewList = new ArrayList<>();
      this.reviewService.getAllReviews().forEach(reviewList::add);
      return reviewList;
    }
  }

  @GetMapping("/{id}")
  public Review getReviewById(@PathVariable("id") Long id) {
    return this.reviewService.getReviewById(id).orElseThrow(() -> new EntityNotFound("Review not found " + id));
  }

  @GetMapping("/approved/{restaurant_id}")
  public List<Review> getApprovedReviewsByRestaurant(@PathVariable("restaurant_id") Long restaurantId) {
    return this.reviewService.getReviewByStatusAndRestaurantId(ReviewStatus.APPROVED, restaurantId);
  }
}
