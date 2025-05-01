package com.my.recipe.dto.ingredients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDTO {
  private Long ingredientId;
  private String ingredientName;
  private String description;
  private Long uomId;
  private String uomName;
  private Boolean isActive;
}
