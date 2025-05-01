package com.my.recipe.mapper;

import com.my.recipe.dto.recipeCategory.RecipeCategoryDTO;
import com.my.recipe.model.RecipeCategories;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecipeCategoryMapper {
  RecipeCategoryDTO toDto(RecipeCategories entity);

  RecipeCategories toEntity(RecipeCategoryDTO dto);

  List<RecipeCategoryDTO> toDtoList(List<RecipeCategories> entityList);

  List<RecipeCategories> toEntityList(List<RecipeCategoryDTO> dtoList);
}
