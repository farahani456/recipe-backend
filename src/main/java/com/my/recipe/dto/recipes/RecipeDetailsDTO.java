package com.my.recipe.dto.recipes;

import com.my.recipe.dto.recipeIngredient.RecipeIngredientDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RecipeDetailsDTO extends RecipeDTO {
  private List<RecipeIngredientDTO> ingredients;
}
