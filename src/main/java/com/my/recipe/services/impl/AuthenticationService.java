package com.my.recipe.services.impl;

import com.my.recipe.dto.auth.LoginRequestDTO;
import com.my.recipe.exception.CustomException;
import com.my.recipe.exception.ErrorCode;
import com.my.recipe.model.Users;
import com.my.recipe.repository.UserRepository;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.security.AuthResponse;
import com.my.recipe.security.SecurityTokenService;
import com.my.recipe.security.SecurityUtil;
import com.my.recipe.services.IAuthenticationService;
import com.my.recipe.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService implements IAuthenticationService {

  private UserRepository userRepository;
  private SecurityTokenService securityTokenService;

  @Override
  public AuthResponse authenticate(LoginRequestDTO requestDTO) {
    Users user = userRepository.findByUsernameIgnoreCase(requestDTO.getUsername()).orElse(null);

    if (user != null && SecurityUtil.matchPassword(requestDTO.getPassword(), user.getPassword())) {
      String token =
          securityTokenService.generateToken(
              user.getUsername(), user.getUserId(), user.getIsAdmin());
      return new AuthResponse(token, user.getUserId(), user.getUsername(), user.getIsAdmin());
    }

    throw new CustomException(
        ErrorCode.INVALID_AUTH, "Invalid credentials", "Username or password is wrong");
  }

  @Override
  public AuthDetailsDTO getAuthDetails(String username) {
    Users user = userRepository.findByUsernameIgnoreCase(username).orElse(null);

    if (!Utils.isExist(user)) {
      throw new CustomException(
          ErrorCode.INVALID_AUTH, "username not found", "Session expired. Please login again");
    }

    if (Utils.isExist(user.getIsDeleted()) && user.getIsDeleted()) {
      throw new CustomException(
          ErrorCode.INVALID_AUTH,
          "deleted account",
          "No account found under this username. If you wish to restore your account, please"
              + " contact administrator");
    }

    if (!Utils.isExist(user.getIsActive())
        || (Utils.isExist(user.getIsActive()) && !user.getIsActive())) {
      throw new CustomException(
          ErrorCode.INVALID_AUTH,
          "inactive account",
          "Your account has been deactivated. please contact administrator for further info");
    }

    return new AuthDetailsDTO(
        user.getUserId(),
        user.getUsername(),
        user.getFullName(),
        user.getEmail(),
        user.getIsAdmin());
  }
}
