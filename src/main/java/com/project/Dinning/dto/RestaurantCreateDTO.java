package com.project.Dinning.dto;

import com.project.Dinning.enums.RestaurantType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for creating a new restaurant without scores
 */
@Getter
@Setter
public class RestaurantCreateDTO {

  @NotBlank(message = "Name cannot be blank")
  @Size(max = 100, message = "Name must be less than 100 characters")
  private String name;

  @NotBlank(message = "Address cannot be blank")
  @Size(max = 255, message = "Address must be less than 255 characters")
  private String address;

  @NotBlank(message = "Zip code cannot be blank")
  @Pattern(regexp = "\\d{8}", message = "Zip code must be 8 digits")
  private String zipCode;

  @NotNull(message = "Type cannot be null")
  private RestaurantType type;
}
