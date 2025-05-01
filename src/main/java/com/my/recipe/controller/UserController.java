package com.my.recipe.controller;

import com.my.recipe.dto.users.UserDTO;
import com.my.recipe.dto.users.UserRequestDTO;
import com.my.recipe.dto.users.UserSearchDTO;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.security.SecurityUtil;
import com.my.recipe.services.IUserServices;
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
@RequestMapping(ApiMapping.V1_USER_BASE_URI)
public class UserController {
  private final IUserServices userServices;

  /**
   * Gets a list of user objects.
   *
   * @param search User search. This field is optional.
   * @param isAdmin User is an admin. This field is optional.
   * @param isActive User is active. This field is optional.
   * @param pageable Pageable object. This field is optional.
   * @return PaginatedResponse of UserDTO.
   */
  @GetMapping("")
  public PaginatedResponse<UserDTO> getList(
      @RequestParam(required = false) String search,
      @RequestParam(required = false) Boolean isAdmin,
      @RequestParam(required = false) Boolean isActive,
      @PageableDefault(size = 10) Pageable pageable) {
    UserSearchDTO searchDTO = new UserSearchDTO(search, isAdmin, isActive);
    PaginatedResponse<UserDTO> response = userServices.getLists(searchDTO, pageable);
    return response;
  }

  /**
   * Gets a user object by ID.
   *
   * @param userId User ID. This is a required field.
   * @return UserDTO object.
   */
  @GetMapping("/{userId}/details")
  public UserDTO getDetails(@PathVariable("userId") Long userId) {
    UserDTO response = userServices.getDetails(userId);
    return response;
  }

  /**
   * Creates a new general user. All users can create general users.
   *
   * @param requestDTO UserRequestDTO containing the user information.
   * @return The ID of the newly created user.
   */
  @PostMapping("/register")
  public Long createUser(@RequestBody UserRequestDTO requestDTO) {
    AuthDetailsDTO authDTO = null;
    Long response = userServices.create(requestDTO, false, authDTO);
    return response;
  }

  /**
   * Creates a new admin user. Only admins can create admin users.
   *
   * @param requestDTO UserRequestDTO containing the user information.
   * @param authentication Authentication object to retrieve the authenticated user.
   * @return The ID of the newly created admin user.
   */
  @PostMapping("/admin/register")
  public Long create(@RequestBody UserRequestDTO requestDTO, Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    Long response = userServices.create(requestDTO, true, authDTO);
    return response;
  }

  /**
   * Updates a user object
   *
   * @param userId User ID. This is a required field.
   * @param requestDTO UserRequestDTO containing the user information.
   * @param authentication Authentication object to retrieve the authenticated user.
   */
  @PostMapping("/{userId}/update")
  public void update(
      @PathVariable("userId") Long userId,
      @RequestBody UserRequestDTO requestDTO,
      Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, false);
    userServices.update(userId, requestDTO, authDTO);
  }

  /**
   * Perform soft delete on a user object. Only admins can perform soft deletes.
   *
   * @param userId User ID. This is a required field.
   * @param authentication Authentication object to retrieve the authenticated user.
   */
  @PostMapping("/{userId}/soft-delete")
  public void performSoftDelete(
      @PathVariable("userId") Long userId, Authentication authentication) {
    AuthDetailsDTO authDTO = SecurityUtil.getAuthenticatedUser(authentication, true);
    userServices.performSoftDelete(userId, authDTO);
  }
}
