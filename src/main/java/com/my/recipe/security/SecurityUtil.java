package com.my.recipe.security;

import com.my.recipe.exception.CustomException;
import com.my.recipe.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityUtil {
  private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

  public static String hashPassword(String rawPassword) {
    return encoder.encode(rawPassword);
  }

  public static Boolean matchPassword(String rawPassword, String hashedPassword) {
    return encoder.matches(rawPassword, hashedPassword);
  }

  public static AuthDetailsDTO getAuthenticatedUser(Authentication auth, Boolean adminOnly) {
    if (auth != null && auth.getPrincipal() instanceof AuthDetailsDTO) {
      AuthDetailsDTO user = (AuthDetailsDTO) auth.getPrincipal();
      if (adminOnly && !user.getIsAdmin()) {
        throw new CustomException(ErrorCode.INVALID_ROLE, "isAdmin false", "Access denied");
      }
      return (AuthDetailsDTO) auth.getPrincipal();
    }
    return null;
  }
}
