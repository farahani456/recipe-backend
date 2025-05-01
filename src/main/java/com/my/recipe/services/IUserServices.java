package com.my.recipe.services;

import com.my.recipe.dto.users.UserDTO;
import com.my.recipe.dto.users.UserRequestDTO;
import com.my.recipe.dto.users.UserSearchDTO;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.utils.PaginatedResponse;
import org.springframework.data.domain.Pageable;

public interface IUserServices {
  PaginatedResponse<UserDTO> getLists(UserSearchDTO searchDTO, Pageable pageable);

  UserDTO getDetails(Long userId);

  Long create(UserRequestDTO requestDTO, Boolean isAdmin, AuthDetailsDTO authDTO);

  void update(Long userId, UserRequestDTO requestDTO, AuthDetailsDTO authDTO);

  void performSoftDelete(Long userId, AuthDetailsDTO authDTO);
}
