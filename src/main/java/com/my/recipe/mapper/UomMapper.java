package com.my.recipe.mapper;

import com.my.recipe.dto.uoms.UomDTO;
import com.my.recipe.model.Uoms;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UomMapper {
  UomDTO toDto(Uoms entity);

  Uoms toEntity(UomDTO dto);

  List<UomDTO> toDtoList(List<Uoms> entityList);

  List<Uoms> toEntityList(List<UomDTO> dtoList);
}
