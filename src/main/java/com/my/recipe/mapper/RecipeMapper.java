package com.my.recipe.mapper;

import com.my.recipe.dto.recipeIngredient.RecipeIngredientDTO;
import com.my.recipe.dto.recipes.RecipeDTO;
import com.my.recipe.dto.recipes.RecipeDetailsDTO;
import com.my.recipe.model.Recipes;
import com.my.recipe.services.IRecipeCategoryServices;
import java.util.List;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecipeMapper {
  @Mapping(
      target = "categoryName",
      expression = "java(resolveCategoryName(entity.getCategoryId(), recipeCategoryServices))")
  RecipeDTO toDto(Recipes entity, @Context IRecipeCategoryServices recipeCategoryServices);

  Recipes toEntity(RecipeDTO dto);

  List<RecipeDTO> toDtoList(
      List<Recipes> entityList, @Context IRecipeCategoryServices recipeCategoryServices);

  List<Recipes> toEntityList(List<RecipeDTO> dtoList);

  @Mapping(target = "ingredients", source = "ingredients")
  RecipeDetailsDTO toDetailsDto(RecipeDTO dto, List<RecipeIngredientDTO> ingredients);

  default String resolveCategoryName(
      Long categoryId, IRecipeCategoryServices recipeCategoryServices) {
    if (categoryId == null) return null;
    return recipeCategoryServices.getCategoryNameById(categoryId);
  }
}
