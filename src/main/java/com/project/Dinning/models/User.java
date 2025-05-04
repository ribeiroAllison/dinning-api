package com.project.Dinning.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Represents a user in the dining review system.
 * Users can have allergies and submit reviews for restaurants.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS", uniqueConstraints = {
    @UniqueConstraint(columnNames = "DISPLAY_NAME")
})
public class User {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "USER_ID")
  private Long id;

  @Column(name = "DISPLAY_NAME")
  @NotBlank(message = "Display name cannot be blank")
  @Size(max = 50, message = "Display name cannot be longer than 50 characters")
  @Basic(optional = false)
  private String displayName;

  @Column(name = "CITY")
  @NotBlank(message = "City cannot be blank")
  @Pattern(regexp = "^[a-zA-Z\\s-]+$", message = "City must contain only letters, spaces, and hyphens")
  @Size(max = 50, message = "City cannot be longer than 50 characters")
  @Basic(optional = false)
  private String city;

  @Column(name = "ZIP_CODE")
  @NotBlank(message = "Zip code cannot be blank")
  @Pattern(regexp = "\\d{8}", message = "ZIP code must be 8 digits")
  @Basic(optional = false)
  private String zipCode;

  @Column(name = "IS_PEANUT_ALLERGY")
  @NotNull(message = "Peanut allergy must be defined")
  @Basic(optional = false)
  private boolean isPeanutAllergy = false;

  @Column(name = "IS_EGG_ALLERGY")
  @NotNull(message = "Egg allergy must be defined")
  @Basic(optional = false)
  private boolean isEggAllergy = false;

  @Column(name = "IS_DAIRY_ALLERGY")
  @NotNull(message = "Dairy allergy must be defined")
  @Basic(optional = false)
  private boolean isDairyAllergy = false;

}
