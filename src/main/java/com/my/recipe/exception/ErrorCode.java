package com.my.recipe.exception;

public class ErrorCode {
  // GENERAL
  public static final String MISSING_ID = "G00001";
  public static final String REQUIRED = "G00002";
  public static final String MISMATCH_ID = "G00003";

  // AUTH
  public static final String INVALID_AUTH = "A00001";
  public static final String INVALID_ROLE = "A00002";
  public static final String ACCESS_DENIED = "A00003";
}
