package com.my.recipe.services;

import com.my.recipe.dto.uoms.UomDTO;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.utils.PaginatedResponse;
import org.springframework.data.domain.Pageable;

public interface IUomServices {
  PaginatedResponse<UomDTO> getLists(String search, Boolean isActive, Pageable pageable);

  UomDTO getDetails(Long uomId);

  Long createUom(UomDTO requestDTO, AuthDetailsDTO authDTO);

  void updateUom(Long uomId, UomDTO requestDTO, AuthDetailsDTO authDTO);

  void performSoftDelete(Long uomId, AuthDetailsDTO authDTO);

  String getUomNameById(Long uomId);
}
