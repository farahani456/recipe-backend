package com.my.recipe.utils;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponse<T> {
  private List<T> content;
  private Long page;
  private Long size;
  private Long totalElements;
  private Long totalPages;
}
