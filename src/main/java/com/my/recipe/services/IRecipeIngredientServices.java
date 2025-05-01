package com.my.recipe.services;

import com.my.recipe.dto.recipeIngredient.RecipeIngredientDTO;
import com.my.recipe.security.AuthDetailsDTO;
import java.util.List;

public interface IRecipeIngredientServices {

  List<RecipeIngredientDTO> getActiveLists(Long recipeId);

  void createOrUpdate(Long recipeId, List<RecipeIngredientDTO> requestDTO, AuthDetailsDTO authDTO);

  void performSoftDelete(List<Long> recipeIngredientId, AuthDetailsDTO authDTO);
}
