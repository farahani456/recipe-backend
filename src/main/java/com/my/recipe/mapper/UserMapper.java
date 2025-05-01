package com.my.recipe.mapper;

import com.my.recipe.dto.users.UserDTO;
import com.my.recipe.dto.users.UserRequestDTO;
import com.my.recipe.model.Users;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  UserDTO toDto(Users entity);

  Users toEntity(UserDTO dto);

  Users toRequestEntity(UserRequestDTO dto);

  List<UserDTO> toDtoList(List<Users> entityList);

  List<Users> toEntityList(List<UserDTO> dtoList);
}
