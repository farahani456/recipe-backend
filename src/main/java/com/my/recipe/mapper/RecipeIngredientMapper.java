package com.my.recipe.mapper;

import com.my.recipe.dto.recipeIngredient.RecipeIngredientDTO;
import com.my.recipe.model.RecipeIngredients;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecipeIngredientMapper {

  RecipeIngredientDTO toDto(RecipeIngredients entity);

  RecipeIngredients toEntity(RecipeIngredientDTO dto);

  List<RecipeIngredientDTO> toDtoList(List<RecipeIngredients> entityList);

  List<RecipeIngredients> toEntityList(List<RecipeIngredientDTO> dtoList);
}
