package com.project.Dinning.controllers;

import org.springframework.web.bind.annotation.*;

import com.project.Dinning.enums.ReviewStatus;
import com.project.Dinning.dto.ReviewDTO;
import com.project.Dinning.dto.ReviewResponseDTO;
import com.project.Dinning.models.Review;
import com.project.Dinning.services.ReviewService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/reviews")
@Tag(name = "Review", description = "Review management APIs")
public class ReviewController {

  private final ReviewService reviewService;

  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @Operation(summary = "Create a new Review", description = "Create a new Review with provided details")
  @ApiResponse(responseCode = "201", description = "Review created successfully", content = @Content(schema = @Schema(implementation = Review.class)))
  @ApiResponse(responseCode = "400", description = "Invalid input")
  @PostMapping("")
  public ResponseEntity<Review> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
    Review newReview = this.reviewService.createReviewFromDTO(reviewDTO);
    return new ResponseEntity<>(newReview, HttpStatus.CREATED);
  }

  @Operation(summary = "Update Review Status", description = "Update the status of a review")
  @ApiResponse(responseCode = "200", description = "Review status updated successfully")
  @ApiResponse(responseCode = "404", description = "Review not found")
  @PutMapping("/admin/{id}/{status}")
  public ResponseEntity<String> updateReviewStatus(@PathVariable("id") Long id,
      @PathVariable("status") ReviewStatus status) {
    this.reviewService.updateReviewStatus(id, status);
    return ResponseEntity.ok("Review status updated successfully");
  }

  @Operation(summary = "Approve a review", description = "Change a Review Status to approved")
  @ApiResponse(responseCode = "200", description = "Review approved successfully")
  @ApiResponse(responseCode = "404", description = "Review not found")
  @PutMapping("/admin/{id}/approve")
  public ResponseEntity<String> changeStatusToApproved(@PathVariable("id") Long id) {
    this.reviewService.updateReviewStatus(id, ReviewStatus.APPROVED);
    return ResponseEntity.ok("Review approved successfully");
  }

  @Operation(summary = "Reject a review", description = "Change a Review Status to rejecte")
  @ApiResponse(responseCode = "200", description = "Review rejected successfully")
  @ApiResponse(responseCode = "404", description = "Review not found")
  @PutMapping("/admin/{id}/reject")
  public ResponseEntity<String> changeStatusToRejected(@PathVariable("id") Long id) {
    this.reviewService.updateReviewStatus(id, ReviewStatus.REJECTED);
    return ResponseEntity.ok("Review rejected successfully");
  }

  // This is an example of manual Pageable creation
  @Operation(summary = "Get all Reviews", description = "Get all Reviews with optional filtering and pagination")
  @ApiResponse(responseCode = "200", description = "List of reviews", content = @Content(schema = @Schema(implementation = Review.class)))
  @ApiResponse(responseCode = "404", description = "No Reviews Found")
  @GetMapping("")
  public ResponseEntity<Page<ReviewResponseDTO>> getReviews(
      @RequestParam(required = false) ReviewStatus status,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sort) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
    return ResponseEntity.ok(this.reviewService.getReviewsDTO(status, pageable));
  }

  @Operation(summary = "Get a Review by ID", description = "Retrieve a Review by its ID")
  @ApiResponse(responseCode = "200", description = "Review found", content = @Content(schema = @Schema(implementation = Review.class)))
  @ApiResponse(responseCode = "404", description = "Review not found")
  @GetMapping("/{id}")
  public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(this.reviewService.getReviewDTOById(id));
  }

  @Operation(summary = "Get approved reviews by restaurant ID", description = "Retrieve approved reviews for a specific restaurant")
  @ApiResponse(responseCode = "200", description = "List of approved reviews", content = @Content(schema = @Schema(implementation = Review.class)))
  @ApiResponse(responseCode = "404", description = "No Reviews Found")
  @GetMapping("/approved/{restaurant_id}")
  public ResponseEntity<Page<ReviewResponseDTO>> getApprovedReviewsByRestaurant(
      @PathVariable("restaurant_id") Long restaurantId,
      @RequestParam(required = false) ReviewStatus status,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sort) {

    Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
    return ResponseEntity.ok(this.reviewService.getByApprovedStatusAndId(restaurantId, pageable));
  }
}
