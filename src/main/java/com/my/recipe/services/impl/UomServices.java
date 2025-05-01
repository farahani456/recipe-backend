package com.my.recipe.services.impl;

import com.my.recipe.dto.uoms.UomDTO;
import com.my.recipe.exception.CustomException;
import com.my.recipe.exception.ErrorCode;
import com.my.recipe.mapper.UomMapper;
import com.my.recipe.model.Uoms;
import com.my.recipe.repository.UomRepository;
import com.my.recipe.repository.specification.UomSpecifications;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.services.IUomServices;
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
public class UomServices implements IUomServices {
  private final UomRepository uomRepository;
  private final UomMapper mapper;

  @Override
  public PaginatedResponse<UomDTO> getLists(String search, Boolean isActive, Pageable pageable) {
    Page<Uoms> page = uomRepository.findAll(UomSpecifications.search(search, isActive), pageable);
    List<UomDTO> dtoList = mapper.toDtoList(page.getContent());

    return PaginationUtil.pageable(dtoList, page);
  }

  @Override
  public UomDTO getDetails(Long uomId) {
    Uoms entity = uomRepository.findById(uomId).orElse(null);
    if (!Utils.isExist(entity)) {
      throw new CustomException(
          ErrorCode.MISSING_ID, "uomId does not exist", "Cannot find details by given ID");
    }

    UomDTO dto = mapper.toDto(entity);
    return dto;
  }

  @Transactional
  @Override
  public Long createUom(UomDTO requestDTO, AuthDetailsDTO auth) {
    validateRequest(requestDTO);
    Uoms entity = mapper.toEntity(requestDTO);
    entity.setIsActive(true);
    entity.setCreatedBy(auth.getUserId());
    entity.setUpdatedBy(auth.getUserId());
    entity.setCreatedDate(LocalDateTime.now());
    entity.setUpdatedDate(LocalDateTime.now());

    Uoms savedUoms = uomRepository.save(entity);
    return savedUoms.getUomId();
  }

  @Transactional
  @Override
  public void updateUom(Long uomId, UomDTO requestDTO, AuthDetailsDTO authDTO) {
    Uoms entity = uomRepository.findById(uomId).orElse(null);
    if (!Utils.isExist(entity)) {
      throw new CustomException(
          ErrorCode.MISSING_ID, "uomId does not exist", "Cannot find details by given ID");
    }

    if (!uomId.equals(requestDTO.getUomId())) {
      throw new CustomException(
          ErrorCode.MISMATCH_ID,
          "pathVariable uomId and request body uomId does not match",
          "Mismatch uom found. Please recheck your request");
    }

    validateRequest(requestDTO);
    if (Utils.isExist(requestDTO.getIsActive())) {
      entity.setIsActive(requestDTO.getIsActive());
    }
    if (Utils.isExist(requestDTO.getUomName())) {
      entity.setUomName(requestDTO.getUomName());
    }
    if (Utils.isExist(requestDTO.getUomCode())) {
      entity.setUomCode(requestDTO.getUomCode());
    }
    if (Utils.isExist(requestDTO.getDescription())) {
      entity.setDescription(requestDTO.getDescription());
    }

    entity.setUpdatedBy(authDTO.getUserId());
    entity.setUpdatedDate(LocalDateTime.now());
    uomRepository.save(entity);
  }

  @Transactional
  @Override
  public void performSoftDelete(Long uomId, AuthDetailsDTO authDTO) {
    Uoms entity = uomRepository.findById(uomId).orElse(null);
    if (!Utils.isExist(entity)) {
      throw new CustomException(
          ErrorCode.MISSING_ID, "uomId does not exist", "Cannot find details by given ID");
    }

    entity.setIsActive(false);
    entity.setIsDeleted(true);
    entity.setDeletedBy(authDTO.getUserId());
    entity.setDeletedDate(LocalDateTime.now());
    uomRepository.save(entity);
  }

  private void validateRequest(UomDTO dto) {
    if (!Utils.isExist(dto.getUomName())) {
      throw new CustomException(
          ErrorCode.REQUIRED, "uomName is required", "Please provide uom name");
    }

    if (!Utils.isExist(dto.getUomCode())) {
      throw new CustomException(
          ErrorCode.REQUIRED, "uomCode is required", "Please provide uom code");
    }
  }

  @Override
  public String getUomNameById(Long uomId) {
    return uomRepository.findById(uomId).map(Uoms::getUomName).orElse(null);
  }
}
