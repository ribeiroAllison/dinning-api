package com.project.Dinning.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFound.class)
  public ResponseEntity<Object> handleEntityNotFoundExceptions(
      EntityNotFound ex, WebRequest request) {
    return createErrorResponse(ex, HttpStatus.NOT_FOUND, "Not Found", request);
  }

  @ExceptionHandler(NoResultsFound.class)
  public ResponseEntity<Object> handleNoResultsFoundExceptions(
      NoResultsFound ex, WebRequest request) {
    return createErrorResponse(ex, HttpStatus.NOT_FOUND, "Not Found", request);
  }

  @ExceptionHandler(EntityAlreadyExists.class)
  public ResponseEntity<Object> handleEntityAlreadyExistsException(
      EntityAlreadyExists ex, WebRequest request) {
    return createErrorResponse(ex, HttpStatus.CONFLICT, "Conflict", request);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> handleIllegalArgumentExceptions(
      IllegalArgumentException ex, WebRequest request) {
    return createErrorResponse(ex, HttpStatus.BAD_REQUEST, "Bad Request", request);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationExceptions(
      MethodArgumentNotValidException ex, WebRequest request) {
    // Extract field-specific validation errors
    Map<String, String> fieldErrors = new HashMap<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

    // Create response body with validation errors
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("error", "Validation Error");
    body.put("message", "Input validation failed");
    body.put("errors", fieldErrors); // Include field-specific errors
    body.put("path", request.getDescription(false).replace("uri=", ""));

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAllUncaughtExceptions(
      Exception ex, WebRequest request) {
    // Log Exception for Debugging
    ex.printStackTrace();

    return createErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", request);
  }

  private ResponseEntity<Object> createErrorResponse(
      Exception ex, HttpStatus status, String error, WebRequest request) {

    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", status.value());
    body.put("error", error);
    body.put("message", ex.getMessage());
    body.put("path", request.getDescription(false).replace("uri=", ""));

    return new ResponseEntity<>(body, status);
  }

}
