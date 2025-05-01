package com.my.recipe.controller;

import com.my.recipe.dto.uoms.UomDTO;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.security.SecurityUtil;
import com.my.recipe.services.IUomServices;
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
@RequestMapping(ApiMapping.V1_UOM_BASE_URI)
public class UomController {
  private final IUomServices uomServices;

  /**
   * Retrieves a paginated list of UOM (Unit of Measurement) objects.
   *
   * @param search Optional search criteria for filtering UOMs.
   * @param isActive Optional flag to filter active UOMs.
   * @param pageable Pageable object to control pagination and sorting.
   * @return PaginatedResponse containing a list of UomDTO objects.
   */
  @GetMapping("")
  public PaginatedResponse<UomDTO> getList(
      @RequestParam(required = false) String search,
      @RequestParam(required = false) Boolean isActive,
      @PageableDefault(size = 10) Pageable pageable) {
    PaginatedResponse<UomDTO> response = uomServices.getLists(search, isActive, pageable);
    return response;
  }

  /**
   * Retrieves the details of a specific Unit of Measurement (UOM) by its ID.
   *
   * @param uomId The ID of the UOM to retrieve details for.
   * @return UomDTO containing the details of the specified UOM.
   */
  @GetMapping("/{uomId}/details")
  public UomDTO getDetails(@PathVariable("uomId") Long uomId) {
    UomDTO response = uomServices.getDetails(uomId);
    return response;
  }

  /**
   * Creates a new Unit of Measurement (UOM). Only admins can create UOMs.
   *
   * @param requestDTO UomDTO containing the details of the UOM to be created.
   * @param authentication Authentication object to retrieve the authenticated user.
   * @return The ID of the newly created UOM.
   */
  @PostMapping("/create")
  public Long createUom(@RequestBody UomDTO requestDTO, Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    Long response = uomServices.createUom(requestDTO, authDTO);
    return response;
  }

  /**
   * Updates an existing Unit of Measurement (UOM) with the specified ID. Only admins can update
   * UOMs.
   *
   * @param uomId The ID of the UOM to update.
   * @param requestDTO UomDTO containing the new details for the UOM.
   * @param authentication Authentication object to retrieve the authenticated user.
   */
  @PostMapping("/{uomId}/update")
  public void updateUom(
      @PathVariable("uomId") Long uomId,
      @RequestBody UomDTO requestDTO,
      Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    uomServices.updateUom(uomId, requestDTO, authDTO);
  }

  /**
   * Perform a soft delete on a UOM. Only admins can perform soft deletes.
   *
   * @param uomId The ID of the UOM to be soft-deleted.
   * @param authentication Authentication object to retrieve the authenticated user.
   */
  @PostMapping("/{uomId}/soft-delete")
  public void performSoftDelete(@PathVariable("uomId") Long uomId, Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    uomServices.performSoftDelete(uomId, authDTO);
  }
}
