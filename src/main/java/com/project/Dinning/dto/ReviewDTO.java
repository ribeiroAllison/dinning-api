package com.project.Dinning.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
  private Long id;

  @NotNull(message = "User ID is required")
  private Long userId;

  @NotNull(message = "Restaurant ID is required")
  private Long restaurantId;

  @Min(value = 1, message = "Peanut score must be at least 1")
  @Max(value = 5, message = "Peanut score must be at most 5")
  private Integer peanutScore;

  @Min(value = 1, message = "Egg score must be at least 1")
  @Max(value = 5, message = "Egg score must be at most 5")
  private Integer eggScore;

  @Min(value = 1, message = "Dairy score must be at least 1")
  @Max(value = 5, message = "Dairy score must be at most 5")
  private Integer dairyScore;

  @Size(max = 255, message = "Commentary must be at most 255 characters")
  private String commentary;

}
