package com.project.Dinning.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

import com.project.Dinning.models.Restaurant;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

  List<Restaurant> findByNameAndZipCode(String name, String zipCode);

  @Query("SELECT r FROM Restaurant r WHERE r.zipCode = :zipCode AND (r.peauntScore IS NOT NULL OR r.eggScore IS NOT NULL OR r.dairyScore IS NOT NULL) ORDER BY r.name DESC")
  List<Restaurant> findByZipCodeAndScoreNotNull(
      @Param("zipCode") String zipCode);

  @Query("SELECT r FROM Restaurant r WHERE r.zipCode = :zipCode AND " +
      "CASE :allergy " +
      "WHEN 'peanut' THEN r.peanutScore IS NOT NULL " +
      "WHEN 'egg' THEN r.eggScore IS NOT NULL " +
      "WHEN 'dairy' THEN r.dairyScore IS NOT NULL " +
      "END")
  List<Restaurant> findByZipCodeAndAllergy(@Param("zipCode") String zipCode, @Param("allergy") String allergy);
}
