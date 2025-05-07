package com.project.Dinning.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {

  @NotBlank
  @Size(max = 255, message = "Name must be less than 255 characters")
  private String displayName;

  @NotBlank
  @Size(max = 60, message = "City must be less than 60 characters")
  private String city;

  @NotBlank
  @Pattern(regexp = "\\d{8}", message = "Zip code must be 8 digits")
  private String zipCode;

  private boolean peanutAllergy;
  private boolean dairyAllergy;
  private boolean eggAllergy;
}
