package com.project.Dinning.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.project.Dinning.models.Restaurant;

public interface RestaurantRepository
    extends CrudRepository<Restaurant, Long>, PagingAndSortingRepository<Restaurant, Long> {

  Page<Restaurant> findByNameAndZipCode(String name, String zipCode, Pageable pageable);

  @Query("SELECT r FROM Restaurant r WHERE r.zipCode = :zipCode AND (r.peanutScore IS NOT NULL OR r.eggScore IS NOT NULL OR r.dairyScore IS NOT NULL) ORDER BY r.name DESC")
  Page<Restaurant> findByZipCodeAndScoreNotNull(
      @Param("zipCode") String zipCode, Pageable pageable);

  @Query("SELECT r FROM Restaurant r WHERE r.zipCode = :zipCode AND " +
      "CASE :allergy " +
      "WHEN 'peanut' THEN r.peanutScore IS NOT NULL " +
      "WHEN 'egg' THEN r.eggScore IS NOT NULL " +
      "WHEN 'dairy' THEN r.dairyScore IS NOT NULL " +
      "END")
  Page<Restaurant> findByZipCodeAndAllergy(@Param("zipCode") String zipCode, @Param("allergy") String allergy,
      Pageable pageable);

  boolean existsByNameAndZipCode(String name, String zipCode);
}
