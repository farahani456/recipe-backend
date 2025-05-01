package com.my.recipe.exception;

public class CustomException extends RuntimeException {
  private int status;
  private String displayMessage;
  private String errorCode;

  public CustomException(String errorCode, String message, String displayMessage) {
    super(message);
    this.displayMessage = displayMessage;
    this.errorCode = errorCode;
  }

  public CustomException(String message) {
    super(message);
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getDisplayMessage() {
    return displayMessage;
  }

  public void setDisplayMessage(String displayMessage) {
    this.displayMessage = displayMessage;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }
}
