package com.my.recipe.controller;

import com.my.recipe.dto.recipeIngredient.RecipeIngredientDTO;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.security.SecurityUtil;
import com.my.recipe.services.IRecipeIngredientServices;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(ApiMapping.V1_RECIPE_CATEGORY_BASE_URI)
public class RecipeIngredientController {
  private final IRecipeIngredientServices recipeIngredientServices;

  /**
   * Get active ingredients by a given recipeId
   *
   * @param recipeId required
   * @return
   */
  @GetMapping("/{recipeId}")
  public List<RecipeIngredientDTO> getList(@PathVariable("recipeId") Long recipeId) {
    List<RecipeIngredientDTO> response = recipeIngredientServices.getActiveLists(recipeId);
    return response;
  }

  /**
   * Create or update recipe's ingredient. Only admins can create or update
   *
   * @apiNote create and update is combined because ingredients is in a list
   * @param recipeId required
   * @param requestDTO List of ingredients
   * @param authentication
   */
  @PostMapping("/{recipeId}/create-or-update")
  public void createOrUpdate(
      @PathVariable("recipeId") Long recipeId,
      @RequestBody List<RecipeIngredientDTO> requestDTO,
      Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    recipeIngredientServices.createOrUpdate(recipeId, requestDTO, authDTO);
  }

  /**
   * Perform soft delete on a list of recipeIngredientId. Only admins can perform soft deletes.
   *
   * @param recipeIngredientIds required at least one
   * @param authentication
   */
  @PostMapping("/{recipeIngredientId}/soft-delete")
  public void performSoftDelete(
      @PathVariable("recipeIngredientId") List<Long> recipeIngredientIds,
      Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    recipeIngredientServices.performSoftDelete(recipeIngredientIds, authDTO);
  }
}
