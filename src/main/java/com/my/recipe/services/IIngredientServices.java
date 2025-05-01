package com.my.recipe.services;

import com.my.recipe.dto.ingredients.IngredientDTO;
import com.my.recipe.dto.ingredients.IngredientSearchDTO;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.utils.PaginatedResponse;
import org.springframework.data.domain.Pageable;

public interface IIngredientServices {
  PaginatedResponse<IngredientDTO> getLists(IngredientSearchDTO searchDTO, Pageable pageable);

  IngredientDTO getDetails(Long ingredientId);

  Long create(IngredientDTO requestDTO, AuthDetailsDTO authDTO);

  void update(Long ingredientId, IngredientDTO requestDTO, AuthDetailsDTO authDTO);

  void performSoftDelete(Long ingredientId, AuthDetailsDTO authDTO);
}
