package com.project.Dinning.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {

  @Size(max = 60, message = "City must be less than 60 characters")
  private String city;

  @Pattern(regexp = "\\d{8}", message = "Zip code must be 8 digits")
  private String zipCode;

  private Boolean peanutAllergy;
  private Boolean dairyAllergy;
  private Boolean eggAllergy;
}
