package com.my.recipe.services.impl;

import com.my.recipe.dto.recipeIngredient.RecipeIngredientDTO;
import com.my.recipe.dto.recipes.RecipeDTO;
import com.my.recipe.dto.recipes.RecipeDetailsDTO;
import com.my.recipe.dto.recipes.RecipeSearchDTO;
import com.my.recipe.exception.CustomException;
import com.my.recipe.exception.ErrorCode;
import com.my.recipe.mapper.RecipeMapper;
import com.my.recipe.model.RecipeImages;
import com.my.recipe.model.Recipes;
import com.my.recipe.repository.RecipeImagesRepository;
import com.my.recipe.repository.RecipeRepository;
import com.my.recipe.repository.specification.RecipeSpecifications;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.services.IRecipeCategoryServices;
import com.my.recipe.services.IRecipeIngredientServices;
import com.my.recipe.services.IRecipeServices;
import com.my.recipe.utils.PaginatedResponse;
import com.my.recipe.utils.PaginationUtil;
import com.my.recipe.utils.Utils;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class RecipeServices implements IRecipeServices {
  private final RecipeRepository recipeRepository;
  private final IRecipeIngredientServices recipeIngredientServices;
  private final IRecipeCategoryServices recipeCategoryServices;
  private final RecipeImagesRepository recipeImagesRepository;
  private final RecipeMapper mapper;

  @Override
  public PaginatedResponse<RecipeDTO> getLists(RecipeSearchDTO searchDTO, Pageable pageable) {
    Page<Recipes> page = recipeRepository.findAll(RecipeSpecifications.search(searchDTO), pageable);
    List<RecipeDTO> dtoList = mapper.toDtoList(page.getContent(), recipeCategoryServices);

    return PaginationUtil.pageable(dtoList, page);
  }

  @Override
  public RecipeDetailsDTO getDetails(Long recipeId) {
    Recipes entity = recipeRepository.findById(recipeId).orElse(null);
    if (!Utils.isExist(entity)) {
      throw new CustomException(
          ErrorCode.MISSING_ID, "recipeId does not exist", "Cannot find details by given ID");
    }

    RecipeDTO dto = mapper.toDto(entity, recipeCategoryServices);
    List<RecipeIngredientDTO> ingredients = recipeIngredientServices.getActiveLists(recipeId);
    RecipeDetailsDTO response = mapper.toDetailsDto(dto, ingredients);
    return response;
  }

  @Override
  @Transactional
  public Long create(RecipeDetailsDTO requestDTO, AuthDetailsDTO auth) {
    validateRequest(requestDTO);

    Recipes entity = new Recipes();
    entity.setTitle(requestDTO.getTitle());
    entity.setDescription(requestDTO.getDescription());
    entity.setInstructions(requestDTO.getInstructions());
    entity.setCategoryId(requestDTO.getCategoryId());
    entity.setIsActive(true);
    entity.setCreatedBy(auth.getUserId());
    entity.setUpdatedBy(auth.getUserId());
    entity.setCreatedDate(LocalDateTime.now());
    entity.setUpdatedDate(LocalDateTime.now());

    if (Utils.isExist(requestDTO.getIsPublished())) {
      entity.setIsPublished(requestDTO.getIsPublished());
    } else {
      entity.setIsPublished(false);
    }

    Recipes savedRecipes = recipeRepository.save(entity);
    if (Utils.isListExist(requestDTO.getIngredients())) {
      recipeIngredientServices.createOrUpdate(
          savedRecipes.getRecipeId(), requestDTO.getIngredients(), auth);
    }
    return savedRecipes.getRecipeId();
  }

  @Transactional
  @Override
  public void update(Long recipeId, RecipeDetailsDTO requestDTO, AuthDetailsDTO auth) {
    Recipes entity = recipeRepository.findById(recipeId).orElse(null);
    if (!Utils.isExist(entity)) {
      throw new CustomException(
          ErrorCode.MISSING_ID, "recipeId does not exist", "Cannot find details by given ID");
    }

    if (!recipeId.equals(requestDTO.getRecipeId())) {
      throw new CustomException(
          ErrorCode.MISMATCH_ID,
          "pathVariable recipeId and request body recipeId does not match",
          "Mismatch recipe found. Please recheck your request");
    }

    validateRequest(requestDTO);
    entity.setTitle(requestDTO.getTitle());
    entity.setDescription(requestDTO.getDescription());
    entity.setInstructions(requestDTO.getInstructions());
    entity.setCategoryId(requestDTO.getCategoryId());
    entity.setUpdatedBy(auth.getUserId());
    entity.setUpdatedDate(LocalDateTime.now());

    if (Utils.isExist(requestDTO.getIsActive())) {
      entity.setIsActive(requestDTO.getIsActive());
    }
    if (Utils.isExist(requestDTO.getIsPublished())) {
      entity.setIsPublished(requestDTO.getIsPublished());
    }

    Recipes savedRecipes = recipeRepository.save(entity);
    if (Utils.isListExist(requestDTO.getIngredients())) {
      recipeIngredientServices.createOrUpdate(
          savedRecipes.getRecipeId(), requestDTO.getIngredients(), auth);
    }
  }

  @Override
  public void performSoftDelete(Long recipeId, AuthDetailsDTO authDTO) {
    Recipes entity = recipeRepository.findById(recipeId).orElse(null);
    if (!Utils.isExist(entity)) {
      throw new CustomException(
          ErrorCode.MISSING_ID, "recipeId does not exist", "Cannot find details by given ID");
    }

    List<RecipeIngredientDTO> ingredients = recipeIngredientServices.getActiveLists(recipeId);
    List<Long> recipeIngredientIds =
        ingredients.stream()
            .map(RecipeIngredientDTO::getRecipeIngredientId)
            .distinct()
            .collect(Collectors.toList());
    if (Utils.isListExist(recipeIngredientIds)) {
      recipeIngredientServices.performSoftDelete(recipeIngredientIds, authDTO);
    }

    entity.setIsActive(false);
    entity.setIsDeleted(true);
    entity.setDeletedBy(authDTO.getUserId());
    entity.setDeletedDate(LocalDateTime.now());
    recipeRepository.save(entity);
  }

  private void validateRequest(RecipeDetailsDTO dto) {
    if (!Utils.isExist(dto.getTitle())) {
      throw new CustomException(ErrorCode.REQUIRED, "title is required", "Please provide title");
    }

    if (!Utils.isExist(dto.getInstructions())) {
      throw new CustomException(
          ErrorCode.REQUIRED, "instructions is required", "Please provide instructions");
    }

    if (!Utils.isExist(dto.getCategoryId())) {
      throw new CustomException(
          ErrorCode.REQUIRED, "recipeId is required", "Please provide category");
    }
  }

  @Override
  public Long uploadImage(Long recipeId, MultipartFile file, AuthDetailsDTO auth) {
    if (file.isEmpty() || !Utils.isExist(recipeId)) {
      throw new CustomException(
          ErrorCode.REQUIRED, "recipeId && file is required", "Please provide image");
    }
    try {
      RecipeImages image = new RecipeImages();
      image.setRecipeId(recipeId);
      image.setName(file.getOriginalFilename());
      image.setContentType(file.getContentType());
      image.setImageData(file.getBytes());
      image.setIsActive(true);
      image.setCreatedBy(auth.getUserId());
      image.setUpdatedBy(auth.getUserId());
      image.setCreatedDate(LocalDateTime.now());
      image.setUpdatedDate(LocalDateTime.now());

      RecipeImages saved = recipeImagesRepository.save(image);
      return saved.getRecipeImageId();
    } catch (IOException e) {
      throw new CustomException(ErrorCode.REQUIRED, "file is required", "Please provide image");
    }
  }

  @Override
  public ResponseEntity<?> getImage(Long recipeImageId) {
    RecipeImages entity = recipeImagesRepository.findById(recipeImageId).orElse(null);
    if (!Utils.isExist(entity)) {
      throw new CustomException(
          ErrorCode.MISSING_ID, "recipeImageId does not exist", "Cannot find image by given ID");
    }

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(entity.getContentType()))
        .body(entity.getImageData());
  }
}
