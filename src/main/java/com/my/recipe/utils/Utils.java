package com.my.recipe.utils;

import java.util.List;

public class Utils {
  public static <T> Boolean isExist(T value) {
    if (value == null) {
      return false;
    }

    if (value instanceof String) {
      if (((String) value).isBlank()) {
        return false;
      }
    }
    return true;
  }

  public static <T> Boolean isListExist(List<T> list) {
    if (list == null || (list != null && list.size() <= 0)) {
      return false;
    }

    return true;
  }
}
