package com.my.recipe.controller;

import com.my.recipe.dto.recipes.RecipeDTO;
import com.my.recipe.dto.recipes.RecipeDetailsDTO;
import com.my.recipe.dto.recipes.RecipeSearchDTO;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.security.SecurityUtil;
import com.my.recipe.services.IRecipeServices;
import com.my.recipe.utils.PaginatedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(ApiMapping.V1_RECIPE_BASE_URI)
public class RecipeController {
  private final IRecipeServices recipeServices;

  public RecipeController(final IRecipeServices recipeServices) {
    this.recipeServices = recipeServices;
  }

  /**
   * Retrieves a paginated list of recipes.
   *
   * @param search Optional search criteria for filtering recipes by their title or description.
   * @param categoryId Optional category ID to filter recipes by specific category.
   * @param isActive Optional flag to filter active recipes.
   * @param isPublished Optional flag to filter published recipes.
   * @param pageable Pageable object to control pagination and sorting.
   * @return PaginatedResponse containing a list of RecipeDTO objects.
   */
  @GetMapping("")
  public PaginatedResponse<RecipeDTO> getList(
      @RequestParam(required = false) String search,
      @RequestParam(required = false) Long categoryId,
      @RequestParam(required = false) Boolean isActive,
      @RequestParam(required = false) Boolean isPublished,
      @PageableDefault(size = 10) Pageable pageable) {
    RecipeSearchDTO searchDTO = new RecipeSearchDTO(search, categoryId, isPublished, isActive);
    PaginatedResponse<RecipeDTO> response = recipeServices.getLists(searchDTO, pageable);
    return response;
  }

  /**
   * Retrieves the details of a specific recipe by its ID.
   *
   * @param recipeId The ID of the recipe to retrieve details for.
   * @return RecipeDetailsDTO containing the details of the specified recipe.
   */
  @GetMapping("/{recipeId}/details")
  public RecipeDetailsDTO getDetails(@PathVariable("recipeId") Long recipeId) {
    RecipeDetailsDTO response = recipeServices.getDetails(recipeId);
    return response;
  }

  /**
   * Creates a new recipe. Only admins can create recipes.
   *
   * @param requestDTO RecipeDetailsDTO containing the details of the recipe to be created.
   * @param authentication Authentication object to retrieve the authenticated user.
   * @return The ID of the newly created recipe.
   */
  @PostMapping("/create")
  public Long create(@RequestBody RecipeDetailsDTO requestDTO, Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    Long response = recipeServices.create(requestDTO, authDTO);
    return response;
  }

  /**
   * Updates an existing recipe with the specified ID. Only admins can update recipes.
   *
   * @param recipeId The ID of the recipe to update.
   * @param requestDTO RecipeDetailsDTO containing the new details for the recipe.
   * @param authentication Authentication object to retrieve the authenticated user.
   */
  @PostMapping("/{recipeId}/update")
  public void update(
      @PathVariable("recipeId") Long recipeId,
      @RequestBody RecipeDetailsDTO requestDTO,
      Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    recipeServices.update(recipeId, requestDTO, authDTO);
  }

  /**
   * Performs a soft delete on a recipe with the specified ID. Only admins can perform soft deletes.
   *
   * @param recipeId The ID of the recipe to be soft-deleted.
   * @param authentication Authentication object to retrieve the authenticated user.
   */
  @PostMapping("/{recipeId}/soft-delete")
  public void performSoftDelete(
      @PathVariable("recipeId") Long recipeId, Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    recipeServices.performSoftDelete(recipeId, authDTO);
  }

  @PostMapping("/{recipeId}/upload")
  public Long uploadImage(
      @PathVariable("recipeId") Long recipeId,
      @RequestParam("file") MultipartFile file,
      Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    Long response = recipeServices.uploadImage(recipeId, file, authDTO);
    return response;
  }

  @GetMapping("/image/{recipeImageId}")
  public ResponseEntity<?> getImage(@PathVariable("recipeImageId") Long recipeImageId) {
    ResponseEntity<?> response = recipeServices.getImage(recipeImageId);
    return response;
  }
}
