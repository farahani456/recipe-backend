package com.my.recipe.utils;

import java.util.List;
import org.springframework.data.domain.Page;

public class PaginationUtil {
  public static <T, P> PaginatedResponse<T> pageable(List<T> dtoList, Page<P> page) {
    return new PaginatedResponse<>(
        dtoList,
        ((long) page.getNumber()),
        ((long) page.getSize()),
        ((long) page.getTotalElements()),
        ((long) page.getTotalPages()));
  }
}
