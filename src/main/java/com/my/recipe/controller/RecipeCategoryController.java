package com.my.recipe.controller;

import com.my.recipe.dto.recipeCategory.RecipeCategoryDTO;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.security.SecurityUtil;
import com.my.recipe.services.IRecipeCategoryServices;
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
@RequestMapping(ApiMapping.V1_RECIPE_CATEGORY_BASE_URI)
public class RecipeCategoryController {
  private final IRecipeCategoryServices recipeCategoryServices;

  /**
   * Retrieves a paginated list of recipe categories.
   *
   * @param search Optional search criteria for filtering recipe categories by their name or
   *     description.
   * @param isActive Optional flag to filter active recipe categories.
   * @param pageable Pageable object to control pagination and sorting.
   * @return PaginatedResponse containing a list of RecipeCategoryDTO objects.
   */
  @GetMapping("")
  public PaginatedResponse<RecipeCategoryDTO> getList(
      @RequestParam(required = false) String search,
      @RequestParam(required = false) Boolean isActive,
      @PageableDefault(size = 10) Pageable pageable) {
    PaginatedResponse<RecipeCategoryDTO> response =
        recipeCategoryServices.getLists(search, isActive, pageable);
    return response;
  }

  /**
   * Retrieves a single recipe category by its ID.
   *
   * @param categoryId Recipe category ID to retrieve.
   * @return RecipeCategoryDTO containing the retrieved recipe category details.
   */
  @GetMapping("/{categoryId}/details")
  public RecipeCategoryDTO getDetails(@PathVariable("categoryId") Long categoryId) {
    RecipeCategoryDTO response = recipeCategoryServices.getDetails(categoryId);
    return response;
  }

  /**
   * Creates a new recipe category. Only admins can create recipe categories.
   *
   * @param requestDTO RecipeCategoryDTO containing the details of the recipe category to be
   *     created.
   * @param authentication Authentication object to retrieve the authenticated user.
   * @return The ID of the newly created recipe category.
   */
  @PostMapping("/create")
  public Long create(@RequestBody RecipeCategoryDTO requestDTO, Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    Long response = recipeCategoryServices.create(requestDTO, authDTO);
    return response;
  }

  /**
   * Updates an existing recipe category with the specified ID. Only admins can update recipe
   * categories.
   *
   * @param categoryId The ID of the recipe category to update.
   * @param requestDTO RecipeCategoryDTO containing the new details for the recipe category.
   * @param authentication Authentication object to retrieve the authenticated user.
   */
  @PostMapping("/{categoryId}/update")
  public void update(
      @PathVariable("categoryId") Long categoryId,
      @RequestBody RecipeCategoryDTO requestDTO,
      Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    recipeCategoryServices.update(categoryId, requestDTO, authDTO);
  }

  /**
   * Performs a soft delete on a recipe category with the specified ID. Only admins can perform soft
   * deletes.
   *
   * @param categoryId The ID of the recipe category to be soft-deleted.
   * @param authentication Authentication object to retrieve the authenticated user.
   */
  @PostMapping("/{categoryId}/soft-delete")
  public void performSoftDelete(
      @PathVariable("categoryId") Long categoryId, Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    recipeCategoryServices.performSoftDelete(categoryId, authDTO);
  }
}
