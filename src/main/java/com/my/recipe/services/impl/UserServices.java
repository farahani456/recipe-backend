package com.my.recipe.services.impl;

import com.my.recipe.dto.users.UserDTO;
import com.my.recipe.dto.users.UserRequestDTO;
import com.my.recipe.dto.users.UserSearchDTO;
import com.my.recipe.exception.CustomException;
import com.my.recipe.exception.ErrorCode;
import com.my.recipe.mapper.UserMapper;
import com.my.recipe.model.Users;
import com.my.recipe.repository.UserRepository;
import com.my.recipe.repository.specification.UserSpecifications;
import com.my.recipe.security.AuthDetailsDTO;
import com.my.recipe.security.SecurityUtil;
import com.my.recipe.services.IUserServices;
import com.my.recipe.utils.PaginatedResponse;
import com.my.recipe.utils.PaginationUtil;
import com.my.recipe.utils.Utils;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserServices implements IUserServices {
  private final UserRepository userRepository;
  private final UserMapper mapper;

  @Override
  public PaginatedResponse<UserDTO> getLists(UserSearchDTO searchDTO, Pageable pageable) {
    Page<Users> page = userRepository.findAll(UserSpecifications.search(searchDTO), pageable);
    List<UserDTO> dtoList = mapper.toDtoList(page.getContent());

    return PaginationUtil.pageable(dtoList, page);
  }

  @Override
  public UserDTO getDetails(Long userId) {
    Users entity = userRepository.findById(userId).orElse(null);
    if (!Utils.isExist(entity)) {
      throw new CustomException(
          ErrorCode.MISSING_ID, "userId does not exist", "Cannot find details by given ID");
    }

    UserDTO dto = mapper.toDto(entity);
    return dto;
  }

  @Transactional
  @Override
  public Long create(UserRequestDTO requestDTO, Boolean isAdmin, AuthDetailsDTO auth) {
    // to make sure user always new
    requestDTO.setUserId(null);
    validateRequest(requestDTO, true);

    Users entity = mapper.toRequestEntity(requestDTO);
    entity.setIsAdmin(isAdmin);
    entity.setIsActive(true);
    entity.setCreatedDate(LocalDateTime.now());
    entity.setUpdatedDate(LocalDateTime.now());

    if (Utils.isExist(auth)) {
      entity.setCreatedBy(auth.getUserId());
      entity.setUpdatedBy(auth.getUserId());
    } else {
      // TODO: handle for public user creation (guest)
      entity.setCreatedBy(1L);
      entity.setUpdatedBy(1L);
    }

    // storing password
    String hashed = SecurityUtil.hashPassword(requestDTO.getPassword());
    entity.setPassword(hashed);

    Users savedUsers = userRepository.save(entity);
    return savedUsers.getUserId();
  }

  @Transactional
  @Override
  public void update(Long userId, UserRequestDTO requestDTO, AuthDetailsDTO auth) {
    Users entity = userRepository.findById(userId).orElse(null);
    if (!Utils.isExist(entity)) {
      throw new CustomException(
          ErrorCode.MISSING_ID, "userId does not exist", "Cannot find details by given ID");
    }

    if (!userId.equals(requestDTO.getUserId())) {
      throw new CustomException(
          ErrorCode.MISMATCH_ID,
          "pathVariable userId and request body userId does not match",
          "Mismatch user found. Please recheck your request");
    }

    if (!auth.getIsAdmin() && !userId.equals(auth.getUserId())) {
      throw new CustomException(
          ErrorCode.ACCESS_DENIED,
          "general user (guest) trying to update other user",
          "Access denied");
    }

    validateRequest(requestDTO, false);
    if (Utils.isExist(requestDTO.getIsActive())) {
      entity.setIsActive(requestDTO.getIsActive());
    }
    if (Utils.isExist(requestDTO.getFullName())) {
      entity.setFullName(requestDTO.getFullName());
    }
    if (Utils.isExist(requestDTO.getEmail())) {
      entity.setEmail(requestDTO.getEmail());
    }

    entity.setUpdatedBy(auth.getUserId());
    entity.setUpdatedDate(LocalDateTime.now());
    userRepository.save(entity);
  }

  @Transactional
  @Override
  public void performSoftDelete(Long userId, AuthDetailsDTO authDTO) {
    Users entity = userRepository.findById(userId).orElse(null);
    if (!Utils.isExist(entity)) {
      throw new CustomException(
          ErrorCode.MISSING_ID, "userId does not exist", "Cannot find details by given ID");
    }

    entity.setIsActive(false);
    entity.setIsDeleted(true);
    entity.setDeletedBy(authDTO.getUserId());
    entity.setDeletedDate(LocalDateTime.now());
    userRepository.save(entity);
  }

  private void validateRequest(UserRequestDTO dto, Boolean isNewRecord) {
    if (isNewRecord && !Utils.isExist(dto.getUsername())) {
      throw new CustomException(
          ErrorCode.REQUIRED, "username is required", "Please provide user name");
    }

    if (isNewRecord && !Utils.isExist(dto.getPassword())) {
      throw new CustomException(
          ErrorCode.REQUIRED, "password is required", "Please provide password");
    }

    if (!Utils.isExist(dto.getFullName())) {
      throw new CustomException(
          ErrorCode.REQUIRED, "fullName is required", "Please provide full name");
    }

    if (!Utils.isExist(dto.getEmail())) {
      throw new CustomException(ErrorCode.REQUIRED, "email is required", "Please provide email");
    }

    if (isNewRecord) {
      Optional<Users> userOptional = userRepository.findByUsernameIgnoreCase(dto.getUsername());
      if (userOptional.isPresent()) {
        throw new CustomException(
            ErrorCode.REQUIRED, "username exist in database", "Username is already taken");
      }
    }
  }
}
