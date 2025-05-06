package com.project.Dinning.mappers;

import com.project.Dinning.dto.ReviewDTO;
import com.project.Dinning.dto.ReviewResponseDTO;
import com.project.Dinning.models.Review;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewMapper {

  /**
   * Convert a Review entity to a ReviewResponseDTO
   */
  public ReviewResponseDTO toDTO(Review review) {
    if (review == null) {
      return null;
    }

    ReviewResponseDTO dto = new ReviewResponseDTO();
    dto.setId(review.getId());

    if (review.getUser() != null) {
      dto.setUserId(review.getUser().getId());
      dto.setUserName(review.getUser().getDisplayName());
    }

    if (review.getRestaurant() != null) {
      dto.setRestaurantId(review.getRestaurant().getId());
      dto.setRestaurantName(review.getRestaurant().getName());
    }

    dto.setPeanutScore(review.getPeanutScore());
    dto.setEggScore(review.getEggScore());
    dto.setDairyScore(review.getDairyScore());
    dto.setCommentary(review.getCommentary());
    dto.setStatus(review.getStatus());

    return dto;

  }

  public Page<ReviewResponseDTO> toDTOPage(Page<Review> reviewPage) {
    List<ReviewResponseDTO> dtos = reviewPage.getContent().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());

    return new PageImpl<>(dtos, reviewPage.getPageable(), reviewPage.getTotalElements());
  }

}
