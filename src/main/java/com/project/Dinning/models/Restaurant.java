package com.project.Dinning.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.project.Dinning.enums.RestaurantType;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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

  @NotBlank(message = "Name cannot be blank")
  @Size(max = 100, message = "Name cannot be longer than 100 characters")
  @Column(name = "NAME")
  @Basic(optional = false)
  private String name;

  @NotBlank(message = "Address cannot be blank")
  @Size(max = 255, message = "Address cannot be longer than 255 characters")
  @Column(name = "ADDRESS")
  @Basic(optional = false)
  private String address;

  @NotBlank(message = "ZIP code is required")
  @Pattern(regexp = "\\d{8}", message = "ZIP code must be 8 digits")
  @Column(name = "ZIP_CODE")
  @Basic(optional = false)
  private String zipCode;

  @NotNull(message = "Restaurant type is required")
  @Column(name = "TYPE")
  @Basic(optional = false)
  @Enumerated(EnumType.STRING)
  private RestaurantType type;

  @Column(name = "PEANUT_SCORE")
  @Basic(optional = true)
  @Min(value = 1, message = "Peanut score must be at least 1")
  @Max(value = 5, message = "Peanut score cannot be greater than 5")
  private Integer peanutScore;

  @Column(name = "EGG_SCORE")
  @Basic(optional = true)
  @Min(value = 1, message = "Egg score must be at least 1")
  @Max(value = 5, message = "Egg score cannot be greater than 5")
  private Integer eggScore;

  @Column(name = "DAIRY_SCORE")
  @Basic(optional = true)
  @Min(value = 1, message = "Dairy score must be at least 1")
  @Max(value = 5, message = "Dairy score cannot be greater than 5")
  private Integer dairyScore;

}
