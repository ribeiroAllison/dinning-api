package com.project.Dinning.enums;

public enum ReviewStatus {
  PENDING("Pending"),
  APPROVED("Approved"),
  REJECTED("Rejected");

  private final String displayName;

  ReviewStatus(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
