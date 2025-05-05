package com.project.Dinning.errors;

public class EntityAlreadyExists extends RuntimeException {
  public EntityAlreadyExists(String message) {
    super(message);
  }
}
