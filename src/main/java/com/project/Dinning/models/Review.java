package com.project.Dinning.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.project.Dinning.enums.ReviewStatus;
import com.project.Dinning.errors.EntityNotFound;

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
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "RESTAURANT_ID", nullable = false)
  private Restaurant restaurant;

  @Column(name = "PEANUT_SCORE")
  @Min(value = 1, message = "Score must be at least 1")
  @Max(value = 5, message = "Score must be at most 5")
  private Integer peauntScore;

  @Column(name = "EGG_SCORE")
  @Min(value = 1, message = "Score must be at least 1")
  @Max(value = 5, message = "Score must be at most 5")
  private Integer eggScore;

  @Column(name = "DAIRY_SCORE")
  @Min(value = 1, message = "Score must be at least 1")
  @Max(value = 5, message = "Score must be at most 5")
  private Integer dairyScore;

  @Column(name = "COMMENTARY")
  @Size(max = 255, message = "Commentary must be at most 255 characters")
  private String commentary;

  @Column(name = "STATUS")
  @Enumerated(EnumType.STRING)
  private ReviewStatus status = ReviewStatus.PENDING;

  public String getReviewerName() {
    if (user == null) {
      throw new EntityNotFound("User not found");
    }

    return user.getDisplayName();

  }

  public String getRestaurantName() {
    if (restaurant == null) {
      throw new EntityNotFound("Restaurant not found");
    } else {
      return restaurant.getName();
    }
  }

}
