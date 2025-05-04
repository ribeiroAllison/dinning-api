package com.project.Dinning.controllers;

import org.springframework.web.bind.annotation.*;

import com.project.Dinning.enums.ReviewStatus;
import com.project.Dinning.models.Review;
import com.project.Dinning.services.ReviewService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

  private final ReviewService reviewService;

  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @PostMapping("")
  public ResponseEntity<Review> createReview(@Valid @RequestBody Review review) {
    Review newReview = this.reviewService.createReview(review);
    return new ResponseEntity<>(newReview, HttpStatus.CREATED);
  }

  @PutMapping("/admin/{id}/{status}")
  public ResponseEntity<String> updateReviewStatus(@PathVariable("id") Long id,
      @PathVariable("status") ReviewStatus status) {
    this.reviewService.updateReviewStatus(id, status);
    return ResponseEntity.ok("Review status updated successfully");
  }

  @PutMapping("/admin/{id}/approve")
  public ResponseEntity<String> changeStatusToApproved(@PathVariable("id") Long id) {
    this.reviewService.changeStatusToApproved(id);
    return ResponseEntity.ok("Review approved successfully");
  }

  @PutMapping("/admin/{id}/reject")
  public ResponseEntity<String> changeStatusToRejected(@PathVariable("id") Long id) {
    this.reviewService.changeStatusToRejected(id);
    return ResponseEntity.ok("Review rejected successfully");
  }

  @GetMapping("")
  public ResponseEntity<List<Review>> getReviews(@RequestParam(required = false) ReviewStatus status) {
    return ResponseEntity.ok(this.reviewService.getReviews(status));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Review> getReviewById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(this.reviewService.getReviewById(id));
  }

  @GetMapping("/approved/{restaurant_id}")
  public ResponseEntity<List<Review>> getApprovedReviewsByRestaurant(@PathVariable("restaurant_id") Long restaurantId) {
    return ResponseEntity.ok(this.reviewService.getByApprovedStatusAndId(restaurantId));
  }
}
