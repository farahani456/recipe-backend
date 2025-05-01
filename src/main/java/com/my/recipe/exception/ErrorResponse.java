package com.my.recipe.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
  private int status;
  private String errorCode;
  private LocalDateTime errorDateTime;
  private String message;
  private String displayMessage;

  public ErrorResponse(int status, String message) {
    this.status = status;
    this.message = message;
    this.errorDateTime = LocalDateTime.now();
  }

  public ErrorResponse(int status, String errorCode, String message, String displayMessage) {
    this.status = status;
    this.errorCode = errorCode;
    this.message = message;
    this.displayMessage = displayMessage;
    this.errorDateTime = LocalDateTime.now();
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getDisplayMessage() {
    return displayMessage;
  }

  public void setDisplayMessage(String displayMessage) {
    this.displayMessage = displayMessage;
  }

  public LocalDateTime getErrorDateTime() {
    return errorDateTime;
  }

  public void setErrorDateTime(LocalDateTime errorDateTime) {
    this.errorDateTime = errorDateTime;
  }
}
