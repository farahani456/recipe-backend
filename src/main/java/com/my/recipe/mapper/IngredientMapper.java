package com.my.recipe.mapper;

import com.my.recipe.dto.ingredients.IngredientDTO;
import com.my.recipe.model.Ingredients;
import com.my.recipe.services.IUomServices;
import java.util.List;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IngredientMapper {
  @Mapping(target = "uomName", expression = "java(resolveUomName(entity.getUomId(), uomService))")
  IngredientDTO toDto(Ingredients entity, @Context IUomServices uomService);

  Ingredients toEntity(IngredientDTO dto);

  List<IngredientDTO> toDtoList(List<Ingredients> entityList, @Context IUomServices uomService);

  List<Ingredients> toEntityList(List<IngredientDTO> dtoList);

  default String resolveUomName(Long uomId, IUomServices uomService) {
    if (uomId == null) return null;
    return uomService.getUomNameById(uomId);
  }
}
