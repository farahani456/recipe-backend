package com.my.recipe.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchDTO {
  private String search;
  private Boolean isAdmin;
  private Boolean isActive;
}
