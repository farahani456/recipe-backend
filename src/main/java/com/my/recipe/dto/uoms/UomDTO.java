package com.my.recipe.dto.uoms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UomDTO {
  private Long uomId;
  private String uomName;
  private String uomCode;
  private String description;
  private Boolean isActive;
  private Boolean isDeleted;
}
