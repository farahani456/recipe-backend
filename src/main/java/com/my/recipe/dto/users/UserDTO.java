package com.my.recipe.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
  private Long userId;
  private String username;
  private String fullName;
  private String email;
  private Boolean isAdmin;
  private Boolean isActive;
}
