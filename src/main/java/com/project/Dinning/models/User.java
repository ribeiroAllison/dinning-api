package com.project.Dinning.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER", uniqueConstraints = {
    @UniqueConstraint(columnNames = "DISPLAY_NAME")
})
public class User {

  @GeneratedValue
  @Id
  @Column(name = "USER_ID")
  private Long id;

  @Column(name = "DISPLAY_NAME")
  private String displayName;

  @Column(name = "CITY")
  private String city;

  @Column(name = "ZIP_CODE")
  private String zipCode;

  @Column(name = "IS_PEANUT_ALLERGY")
  private Boolean isPeanutAllergy;

  @Column(name = "IS_EGG_ALLERGY")
  private Boolean isEggAllergy;

  @Column(name = "IS_DAIRY_ALLERGY")
  private Boolean isDairyAllergy;

}
