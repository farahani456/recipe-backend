package com.my.recipe.controller;

import com.my.recipe.dto.ingredients.IngredientDTO;
import com.my.recipe.dto.ingredients.IngredientSearchDTO;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.security.SecurityUtil;
import com.my.recipe.services.IIngredientServices;
import com.my.recipe.utils.PaginatedResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(ApiMapping.V1_INGREDIENT_BASE_URI)
public class IngredientController {
  private final IIngredientServices ingredientServices;

  /**
   * Retrieves a paginated list of ingredients.
   *
   * @param search Optional search criteria for filtering ingredients by their name.
   * @param uomId Optional UOM ID to filter ingredients by their unit of measurement.
   * @param isActive Optional flag to filter active ingredients.
   * @param pageable Pageable object to control pagination and sorting.
   * @return PaginatedResponse containing a list of IngredientDTO objects.
   */
  @GetMapping("")
  public PaginatedResponse<IngredientDTO> getList(
      @RequestParam(required = false) String search,
      @RequestParam(required = false) Long uomId,
      @RequestParam(required = false) Boolean isActive,
      @PageableDefault(size = 10) Pageable pageable) {
    IngredientSearchDTO searchDTO = new IngredientSearchDTO(search, uomId, isActive);
    PaginatedResponse<IngredientDTO> response = ingredientServices.getLists(searchDTO, pageable);
    return response;
  }

  /**
   * Retrieves an IngredientDTO object by its ingredientId.
   *
   * @param ingredientId The ID of the ingredient to retrieve.
   * @return IngredientDTO object containing the ingredient details.
   */
  @GetMapping("/{ingredientId}/details")
  public IngredientDTO getDetails(@PathVariable("ingredientId") Long ingredientId) {
    IngredientDTO response = ingredientServices.getDetails(ingredientId);
    return response;
  }

  /**
   * Creates a new ingredient. Only admins can create ingredients.
   *
   * @param requestDTO IngredientDTO containing the details of the ingredient to be created.
   * @param authentication Authentication object to retrieve the authenticated user.
   * @return The ID of the newly created ingredient.
   */
  @PostMapping("/create")
  public Long create(@RequestBody IngredientDTO requestDTO, Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    Long response = ingredientServices.create(requestDTO, authDTO);
    return response;
  }

  /**
   * Updates an existing ingredient with the specified ID. Only admins can update ingredients.
   *
   * @param ingredientId The ID of the ingredient to update.
   * @param requestDTO IngredientDTO containing the new details for the ingredient.
   * @param authentication Authentication object to retrieve the authenticated user.
   */
  @PostMapping("/{ingredientId}/update")
  public void update(
      @PathVariable("ingredientId") Long ingredientId,
      @RequestBody IngredientDTO requestDTO,
      Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    ingredientServices.update(ingredientId, requestDTO, authDTO);
  }

  /**
   * Performs a soft delete on a ingredient with the specified ID. Only admins can perform soft
   * deletes.
   *
   * @param ingredientId The ID of the ingredient to be soft-deleted.
   * @param authentication Authentication object to retrieve the authenticated user.
   */
  @PostMapping("/{ingredientId}/soft-delete")
  public void performSoftDelete(
      @PathVariable("ingredientId") Long ingredientId, Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    ingredientServices.performSoftDelete(ingredientId, authDTO);
  }
}
