package com.project.Dinning.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.project.Dinning.enums.ReviewStatus;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ADMIN_REVIEW")
public class AdminReview {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "ADMIN_REVIEW_ID")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REVIEW_ID", nullable = false)
  private Review review;

  @Column(name = "STATUS")
  @Enumerated(EnumType.STRING)
  private ReviewStatus status = ReviewStatus.PENDING;

}
