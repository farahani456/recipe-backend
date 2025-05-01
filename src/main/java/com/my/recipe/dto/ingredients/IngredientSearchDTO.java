package com.my.recipe.dto.ingredients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientSearchDTO {
  private String search;
  private Long uomId;
  private Boolean isActive;
}
