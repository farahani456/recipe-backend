package com.my.recipe.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDetailsDTO {
  private Long userId;
  private String username;
  private String fullName;
  private String email;
  private Boolean isAdmin;
}
