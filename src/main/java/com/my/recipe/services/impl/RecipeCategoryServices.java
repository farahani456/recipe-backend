package com.my.recipe.services.impl;

import com.my.recipe.dto.recipeCategory.RecipeCategoryDTO;
import com.my.recipe.exception.CustomException;
import com.my.recipe.exception.ErrorCode;
import com.my.recipe.mapper.RecipeCategoryMapper;
import com.my.recipe.model.RecipeCategories;
import com.my.recipe.repository.RecipeCategoryRepository;
import com.my.recipe.repository.specification.RecipeCategorySpecifications;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.services.IRecipeCategoryServices;
import com.my.recipe.utils.PaginatedResponse;
import com.my.recipe.utils.PaginationUtil;
import com.my.recipe.utils.Utils;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RecipeCategoryServices implements IRecipeCategoryServices {
  private final RecipeCategoryRepository recipeCategoryRepository;
  private final RecipeCategoryMapper mapper;

  @Override
  public PaginatedResponse<RecipeCategoryDTO> getLists(
      String search, Boolean isActive, Pageable pageable) {
    Page<RecipeCategories> page =
        recipeCategoryRepository.findAll(
            RecipeCategorySpecifications.search(search, isActive), pageable);
    List<RecipeCategoryDTO> dtoList = mapper.toDtoList(page.getContent());

    return PaginationUtil.pageable(dtoList, page);
  }

  @Override
  public RecipeCategoryDTO getDetails(Long categoryId) {
    RecipeCategories entity = recipeCategoryRepository.findById(categoryId).orElse(null);
    if (!Utils.isExist(entity)) {
      throw new CustomException(
          ErrorCode.MISSING_ID, "categoryId does not exist", "Cannot find details by given ID");
    }

    RecipeCategoryDTO dto = mapper.toDto(entity);
    return dto;
  }

  @Override
  @Transactional
  public Long create(RecipeCategoryDTO requestDTO, AuthDetailsDTO auth) {
    validateRequest(requestDTO);
    RecipeCategories entity = mapper.toEntity(requestDTO);
    entity.setIsActive(true);
    entity.setCreatedBy(auth.getUserId());
    entity.setUpdatedBy(auth.getUserId());
    entity.setCreatedDate(LocalDateTime.now());
    entity.setUpdatedDate(LocalDateTime.now());

    RecipeCategories savedRecipeCategories = recipeCategoryRepository.save(entity);
    return savedRecipeCategories.getCategoryId();
  }

  @Transactional
  @Override
  public void update(Long categoryId, RecipeCategoryDTO requestDTO, AuthDetailsDTO authDTO) {
    RecipeCategories entity = recipeCategoryRepository.findById(categoryId).orElse(null);
    if (!Utils.isExist(entity)) {
      throw new CustomException(
          ErrorCode.MISSING_ID, "categoryId does not exist", "Cannot find details by given ID");
    }
    if (!categoryId.equals(requestDTO.getCategoryId())) {
      throw new CustomException(
          ErrorCode.MISMATCH_ID,
          "pathVariable categoryId and request body categoryId does not match",
          "Mismatch category found. Please recheck your request");
    }

    validateRequest(requestDTO);
    if (Utils.isExist(requestDTO.getIsActive())) {
      entity.setIsActive(requestDTO.getIsActive());
    }
    if (Utils.isExist(requestDTO.getCategoryName())) {
      entity.setCategoryName(requestDTO.getCategoryName());
    }
    if (Utils.isExist(requestDTO.getDescription())) {
      entity.setDescription(requestDTO.getDescription());
    }

    entity.setUpdatedBy(authDTO.getUserId());
    entity.setUpdatedDate(LocalDateTime.now());
    recipeCategoryRepository.save(entity);
  }

  @Override
  public void performSoftDelete(Long categoryId, AuthDetailsDTO authDTO) {
    RecipeCategories entity = recipeCategoryRepository.findById(categoryId).orElse(null);
    if (!Utils.isExist(entity)) {
      throw new CustomException(
          ErrorCode.MISSING_ID, "categoryId does not exist", "Cannot find details by given ID");
    }

    entity.setIsActive(false);
    entity.setIsDeleted(true);
    entity.setDeletedBy(authDTO.getUserId());
    entity.setDeletedDate(LocalDateTime.now());
    recipeCategoryRepository.save(entity);
  }

  private void validateRequest(RecipeCategoryDTO dto) {
    if (!Utils.isExist(dto.getCategoryName())) {
      throw new CustomException(
          ErrorCode.REQUIRED, "categoryName is required", "Please provide category name");
    }
  }

  @Override
  public String getCategoryNameById(Long categoryId) {
    return recipeCategoryRepository
        .findById(categoryId)
        .map(RecipeCategories::getCategoryName)
        .orElse(null);
  }
}
