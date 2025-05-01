package com.my.recipe.dto.recipes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
  private Long recipeId;
  private String title;
  private String description;
  private String instructions;
  private Long categoryId;
  private String categoryName;
  private Boolean isPublished;
  private Boolean isActive;
}
