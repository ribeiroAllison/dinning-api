package com.project.Dinning.dto;

import com.project.Dinning.enums.ReviewStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDTO {
  private Long id;
  private Long userId;
  private String userName;
  private Long restaurantId;
  private String restaurantName;
  private Integer peanutScore;
  private Integer eggScore;
  private Integer dairyScore;
  private String commentary;
  private ReviewStatus status;
}
