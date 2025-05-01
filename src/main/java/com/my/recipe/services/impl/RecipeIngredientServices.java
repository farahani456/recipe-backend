package com.my.recipe.services.impl;

import com.my.recipe.dto.recipeIngredient.RecipeIngredientDTO;
import com.my.recipe.exception.CustomException;
import com.my.recipe.exception.ErrorCode;
import com.my.recipe.mapper.RecipeIngredientMapper;
import com.my.recipe.model.Ingredients;
import com.my.recipe.model.RecipeIngredients;
import com.my.recipe.model.Uoms;
import com.my.recipe.repository.IngredientRepository;
import com.my.recipe.repository.RecipeIngredientRepository;
import com.my.recipe.repository.UomRepository;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.services.IRecipeIngredientServices;
import com.my.recipe.utils.Utils;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RecipeIngredientServices implements IRecipeIngredientServices {
  private final RecipeIngredientRepository recipeIngredientRepository;
  private final IngredientRepository ingredientRepository;
  private final UomRepository uomRepository;
  private final RecipeIngredientMapper mapper;

  @Override
  public List<RecipeIngredientDTO> getActiveLists(Long recipeId) {
    List<RecipeIngredients> entityList =
        recipeIngredientRepository.findByRecipeIdAndIsActiveTrue(recipeId);
    List<RecipeIngredientDTO> dtoList = mapper.toDtoList(entityList);

    List<Long> ingredientIds =
        dtoList.stream()
            .map(RecipeIngredientDTO::getIngredientId)
            .distinct()
            .collect(Collectors.toList());
    List<Ingredients> ingredients = ingredientRepository.findAllById(ingredientIds);

    dtoList.forEach(
        dto -> {
          if (Utils.isExist(dto.getIngredientId())) {
            Ingredients ingredient =
                ingredients.stream()
                    .filter(d -> d.getIngredientId().equals(dto.getIngredientId()))
                    .findFirst()
                    .orElse(null);
            if (Utils.isExist(ingredient)) {
              dto.setIngredientName(ingredient.getIngredientName());

              if (Utils.isExist(ingredient.getUomId())) {
                Uoms uom = uomRepository.findById(ingredient.getUomId()).orElse(null);
                if (Utils.isExist(uom)) {
                  dto.setUomId(uom.getUomId());
                  dto.setUomName(uom.getUomName());
                }
              }
            }
          }
        });
    return dtoList;
  }

  @Transactional
  @Override
  public void createOrUpdate(
      Long recipeId, List<RecipeIngredientDTO> requestListDTO, AuthDetailsDTO auth) {
    validateRequest(recipeId, requestListDTO);
    List<RecipeIngredients> entityList = new ArrayList<>();
    for (RecipeIngredientDTO recIngredient : requestListDTO) {
      RecipeIngredients entity = mapper.toEntity(recIngredient);

      if (!Utils.isExist(recIngredient.getRecipeIngredientId())) {
        entity.setIsActive(true);
        entity.setCreatedBy(auth.getUserId());
        entity.setCreatedDate(LocalDateTime.now());
      }

      entity.setRecipeId(recipeId);
      entity.setUpdatedBy(auth.getUserId());
      entity.setUpdatedDate(LocalDateTime.now());
      entityList.add(entity);
    }
    recipeIngredientRepository.saveAll(entityList);
  }

  private void validateRequest(Long recipeId, List<RecipeIngredientDTO> requestListDTO) {
    if (!Utils.isExist(recipeId)) {
      throw new CustomException(
          ErrorCode.MISSING_ID,
          "recipeId is required",
          "Please provide recipe for the ingredients");
    }

    for (RecipeIngredientDTO recIngredient : requestListDTO) {
      if (!Utils.isExist(recIngredient.getIngredientId())) {
        throw new CustomException(
            ErrorCode.REQUIRED, "ingredientId is required", "Please provide ingredients");
      }
      if (!Utils.isExist(recIngredient.getQuantity())) {
        throw new CustomException(
            ErrorCode.REQUIRED, "quantity is required", "Please provide quantity");
      }
    }
  }

  @Override
  public void performSoftDelete(List<Long> recipeIngredientIds, AuthDetailsDTO authDTO) {
    List<RecipeIngredients> entityList =
        recipeIngredientRepository.findAllById(recipeIngredientIds);
    if (!Utils.isListExist(entityList)) {
      throw new CustomException(
          ErrorCode.MISSING_ID,
          "recipeIngredientIds does not exist",
          "Cannot find details by given IDS");
    }

    entityList.forEach(
        entity -> {
          entity.setIsActive(false);
          entity.setIsDeleted(true);
          entity.setDeletedBy(authDTO.getUserId());
          entity.setDeletedDate(LocalDateTime.now());
        });

    recipeIngredientRepository.saveAll(entityList);
  }
}
