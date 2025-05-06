package com.project.Dinning.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.project.Dinning.enums.ReviewStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "REVIEW")
public class Review {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "REVIEW_ID", nullable = false, updatable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID", nullable = false)
  @NotNull(message = "User is required")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "RESTAURANT_ID", nullable = false)
  @NotNull(message = "Restaurant is required")
  private Restaurant restaurant;

  @Column(name = "PEANUT_SCORE")
  @Basic(optional = true)
  @Min(value = 1, message = "Score must be at least 1")
  @Max(value = 5, message = "Score must be at most 5")
  private Integer peanutScore;

  @Column(name = "EGG_SCORE")
  @Basic(optional = true)
  @Min(value = 1, message = "Score must be at least 1")
  @Max(value = 5, message = "Score must be at most 5")
  private Integer eggScore;

  @Column(name = "DAIRY_SCORE")
  @Basic(optional = true)
  @Min(value = 1, message = "Score must be at least 1")
  @Max(value = 5, message = "Score must be at most 5")
  private Integer dairyScore;

  @Column(name = "COMMENTARY")
  @Basic(optional = true)
  @Size(max = 255, message = "Commentary must be at most 255 characters")
  private String commentary;

  @Column(name = "STATUS")
  @Enumerated(EnumType.STRING)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private ReviewStatus status = ReviewStatus.PENDING;

  @AssertTrue(message = "At least one score must be provided")
  private boolean isAtLeastOneScoreProvided() {
    return peanutScore != null || eggScore != null || dairyScore != null;
  }

}
