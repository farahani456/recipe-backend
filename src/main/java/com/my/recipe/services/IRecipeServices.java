package com.my.recipe.services;

import com.my.recipe.dto.recipes.RecipeDTO;
import com.my.recipe.dto.recipes.RecipeDetailsDTO;
import com.my.recipe.dto.recipes.RecipeSearchDTO;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.utils.PaginatedResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IRecipeServices {
  PaginatedResponse<RecipeDTO> getLists(RecipeSearchDTO searchDTO, Pageable pageable);

  RecipeDetailsDTO getDetails(Long recipeId);

  Long create(RecipeDetailsDTO requestDTO, AuthDetailsDTO authDTO);

  void update(Long recipeId, RecipeDetailsDTO requestDTO, AuthDetailsDTO authDTO);

  void performSoftDelete(Long recipeId, AuthDetailsDTO authDTO);

  Long uploadImage(Long recipeId, MultipartFile file, AuthDetailsDTO auth);

  ResponseEntity<?> getImage(Long recipeImageId);
}
