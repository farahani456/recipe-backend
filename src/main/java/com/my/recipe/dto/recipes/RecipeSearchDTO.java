package com.my.recipe.dto.recipes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeSearchDTO {
  private String search;
  private Long categoryId;
  private Boolean isPublished;
  private Boolean isActive;
}
