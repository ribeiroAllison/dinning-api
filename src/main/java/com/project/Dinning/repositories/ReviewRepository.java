package com.project.Dinning.repositories;

import com.project.Dinning.enums.ReviewStatus;
import com.project.Dinning.models.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReviewRepository extends CrudRepository<Review, Long>, PagingAndSortingRepository<Review, Long> {

  Page<Review> findByStatus(ReviewStatus status, Pageable pageable);

  Page<Review> findByStatusAndRestaurant_Id(ReviewStatus status, Long restaurantId, Pageable pageable);

}
