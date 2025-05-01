package com.my.recipe.dto.recipeIngredient;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredientDTO {
  private Long recipeIngredientId;
  private Long recipeId;
  private Long ingredientId;
  private Long uomId;
  private String ingredientName;
  private String uomName;
  private String description;
  private BigDecimal quantity;
}
