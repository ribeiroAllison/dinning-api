package com.project.Dinning.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.project.Dinning.enums.RestaurantType;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RESTAURANT")
public class Restaurant {

  @GeneratedValue
  @Id
  @Column(name = "RESTAURANT_ID")
  private Long id;

  @Column(name = "NAME")
  @Basic(optional = false)
  private String name;

  @Column(name = "ADDRESS")
  @Basic(optional = false)
  private String address;

  @Column(name = "ZIP_CODE")
  @Basic(optional = false)
  private String zipCode;

  @Column(name = "TYPE")
  @Basic(optional = false)
  @Enumerated(EnumType.STRING)
  private RestaurantType type;

  @Column(name = "PEANUT_SCORE")
  @Basic(optional = true)
  @Min(1)
  @Max(5)
  private Integer peanutScore;

  @Column(name = "EGG_SCORE")
  @Basic(optional = true)
  @Min(1)
  @Max(5)
  private Integer eggScore;

  @Column(name = "DAIRY_SCORE")
  @Basic(optional = true)
  @Min(1)
  @Max(5)
  private Integer dairyScore;

}
