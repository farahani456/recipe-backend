package com.my.recipe.services;

import com.my.recipe.dto.recipeCategory.RecipeCategoryDTO;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.utils.PaginatedResponse;
import org.springframework.data.domain.Pageable;

public interface IRecipeCategoryServices {
  PaginatedResponse<RecipeCategoryDTO> getLists(String search, Boolean isActive, Pageable pageable);

  RecipeCategoryDTO getDetails(Long recipeCategoryId);

  Long create(RecipeCategoryDTO requestDTO, AuthDetailsDTO authDTO);

  void update(Long recipeCategoryId, RecipeCategoryDTO requestDTO, AuthDetailsDTO authDTO);

  void performSoftDelete(Long recipeCategoryId, AuthDetailsDTO authDTO);

  String getCategoryNameById(Long categoryId);
}
