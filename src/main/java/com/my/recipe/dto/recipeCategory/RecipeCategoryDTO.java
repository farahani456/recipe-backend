package com.my.recipe.dto.recipeCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeCategoryDTO {
  private Long categoryId;
  private String categoryName;
  private String description;
  private Boolean isActive;
}
