package com.my.recipe.controller;

import com.my.recipe.dto.auth.LoginRequestDTO;
import com.my.recipe.security.AuthResponse;
import com.my.recipe.services.IAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMapping.V1_AUTH_BASE_URI)
@AllArgsConstructor
public class AuthenticationController {

  private IAuthenticationService authenticationService;

  /**
   * Perform login user and return token after successful authentication
   *
   * @param requestDTO username and password is required
   * @return AuthResponse including token
   */
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequestDTO requestDTO) {
    AuthResponse jwtResponse = authenticationService.authenticate(requestDTO);
    return ResponseEntity.ok(jwtResponse);
  }
}
