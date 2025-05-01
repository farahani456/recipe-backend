package com.my.recipe.services.impl;

import com.my.recipe.dto.ingredients.IngredientDTO;
import com.my.recipe.dto.ingredients.IngredientSearchDTO;
import com.my.recipe.exception.CustomException;
import com.my.recipe.exception.ErrorCode;
import com.my.recipe.mapper.IngredientMapper;
import com.my.recipe.model.Ingredients;
import com.my.recipe.repository.IngredientRepository;
import com.my.recipe.repository.specification.IngredientSpecifications;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.services.IIngredientServices;
import com.my.recipe.services.IUomServices;
import com.my.recipe.utils.PaginatedResponse;
import com.my.recipe.utils.PaginationUtil;
import com.my.recipe.utils.Utils;
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
public class IngredientServices implements IIngredientServices {
  private final IngredientRepository ingredientRepository;
  private final IngredientMapper mapper;
  private final IUomServices uomServices;

  @Override
  public PaginatedResponse<IngredientDTO> getLists(
      IngredientSearchDTO searchDTO, Pageable pageable) {
    Page<Ingredients> page =
        ingredientRepository.findAll(IngredientSpecifications.search(searchDTO), pageable);
    List<IngredientDTO> dtoList = mapper.toDtoList(page.getContent(), uomServices);

    return PaginationUtil.pageable(dtoList, page);
  }

  @Override
  public IngredientDTO getDetails(Long ingredientId) {
    Ingredients entity = ingredientRepository.findById(ingredientId).orElse(null);
    if (!Utils.isExist(entity)) {
      throw new CustomException(
          ErrorCode.MISSING_ID, "ingredientId does not exist", "Cannot find details by given ID");
    }

    IngredientDTO dto = mapper.toDto(entity, uomServices);
    return dto;
  }

  @Override
  public Long create(IngredientDTO requestDTO, AuthDetailsDTO auth) {
    validateRequest(requestDTO);
    Ingredients entity = mapper.toEntity(requestDTO);
    entity.setIsActive(true);
    entity.setCreatedBy(auth.getUserId());
    entity.setUpdatedBy(auth.getUserId());
    entity.setCreatedDate(LocalDateTime.now());
    entity.setUpdatedDate(LocalDateTime.now());

    Ingredients savedIngredients = ingredientRepository.save(entity);
    return savedIngredients.getIngredientId();
  }

  @Override
  public void update(Long ingredientId, IngredientDTO requestDTO, AuthDetailsDTO authDTO) {
    Ingredients entity = ingredientRepository.findById(ingredientId).orElse(null);
    if (!Utils.isExist(entity)) {
      throw new CustomException(
          ErrorCode.MISSING_ID, "ingredientId does not exist", "Cannot find details by given ID");
    }

    if (!ingredientId.equals(requestDTO.getIngredientId())) {
      throw new CustomException(
          ErrorCode.MISMATCH_ID,
          "pathVariable ingredientId and request body ingredientId does not match",
          "Mismatch ingredients found. Please recheck your request");
    }

    validateRequest(requestDTO);
    if (Utils.isExist(requestDTO.getIsActive())) {
      entity.setIsActive(requestDTO.getIsActive());
    }
    if (Utils.isExist(requestDTO.getIngredientName())) {
      entity.setIngredientName(requestDTO.getIngredientName());
    }
    if (Utils.isExist(requestDTO.getUomId())) {
      entity.setUomId(requestDTO.getUomId());
    }
    if (Utils.isExist(requestDTO.getDescription())) {
      entity.setDescription(requestDTO.getDescription());
    }

    ingredientRepository.save(entity);
  }

  @Override
  public void performSoftDelete(Long ingredientId, AuthDetailsDTO authDTO) {
    Ingredients entity = ingredientRepository.findById(ingredientId).orElse(null);
    if (!Utils.isExist(entity)) {
      throw new CustomException(
          ErrorCode.MISSING_ID, "ingredientId does not exist", "Cannot find details by given ID");
    }

    entity.setIsActive(false);
    entity.setIsDeleted(true);
    entity.setDeletedBy(authDTO.getUserId());
    entity.setDeletedDate(LocalDateTime.now());
    ingredientRepository.save(entity);
  }

  private void validateRequest(IngredientDTO dto) {
    if (!Utils.isExist(dto.getIngredientName())) {
      throw new CustomException(
          ErrorCode.REQUIRED, "ingredientName is required", "Please provide ingredient name");
    }

    if (!Utils.isExist(dto.getUomId())) {
      throw new CustomException(ErrorCode.REQUIRED, "uomId is required", "Please provide uom");
    }
  }
}
