package com.project.Dinning.controllers;

import org.springframework.web.bind.annotation.*;

import com.project.Dinning.enums.ReviewStatus;
import com.project.Dinning.models.Review;
import com.project.Dinning.services.ReviewService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    this.reviewService.updateReviewStatus(id, ReviewStatus.APPROVED);
    return ResponseEntity.ok("Review approved successfully");
  }

  @PutMapping("/admin/{id}/reject")
  public ResponseEntity<String> changeStatusToRejected(@PathVariable("id") Long id) {
    this.reviewService.updateReviewStatus(id, ReviewStatus.REJECTED);
    return ResponseEntity.ok("Review rejected successfully");
  }

  // This is an example of manual Pageable creation
  @GetMapping("")
  public ResponseEntity<Page<Review>> getReviews(
      @RequestParam(required = false) ReviewStatus status,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sort) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
    return ResponseEntity.ok(this.reviewService.getReviews(status, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Review> getReviewById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(this.reviewService.getReviewById(id));
  }

  // This is getting its Pageable set up from properties file and automatically
  // implementing it
  @GetMapping("/approved/{restaurant_id}")
  public ResponseEntity<Page<Review>> getApprovedReviewsByRestaurant(
      @PathVariable("restaurant_id") Long restaurantId, Pageable pageable) {
    return ResponseEntity.ok(this.reviewService.getByApprovedStatusAndId(restaurantId, pageable));
  }
}
