package com.my.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.getErrorCode(),
            ex.getMessage(),
            ex.getDisplayMessage());
    return new ResponseEntity<>(
        errorResponse, HttpStatus.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
    HttpStatus status = resolveHttpStatus(ex);
    CustomException customException =
        new CustomException("An unexpected error occurred: " + ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(status.value(), customException.getMessage());

    return new ResponseEntity<>(errorResponse, status);
  }

  private HttpStatus resolveHttpStatus(Exception ex) {
    ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
    if (responseStatus != null) {
      return responseStatus.value();
    }
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
