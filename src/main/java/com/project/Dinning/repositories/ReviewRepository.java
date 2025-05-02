package com.project.Dinning.repositories;

import com.project.Dinning.enums.ReviewStatus;
import com.project.Dinning.models.Review;

import java.util.List;

import org.springframework.data.repository.CrudRepository;;

public interface ReviewRepository extends CrudRepository<Review, Long> {

  List<Review> findByStatus(ReviewStatus status);

  List<Review> findByStatusAndRestaurant_Id(ReviewStatus status, Long restaurantId);

}
