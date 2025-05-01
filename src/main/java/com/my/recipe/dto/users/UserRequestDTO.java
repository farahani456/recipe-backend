package com.my.recipe.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
  private Long userId;
  private String username;
  private String fullName;
  private String email;
  private Boolean isActive;
  private String password;
}
