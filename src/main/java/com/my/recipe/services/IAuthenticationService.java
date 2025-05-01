package com.my.recipe.services;

import com.my.recipe.dto.auth.LoginRequestDTO;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.security.AuthResponse;

public interface IAuthenticationService {
  AuthResponse authenticate(LoginRequestDTO requestDTO);

  AuthDetailsDTO getAuthDetails(String username);
}
