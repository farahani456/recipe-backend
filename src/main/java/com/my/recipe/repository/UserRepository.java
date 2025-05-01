package com.my.recipe.repository;

import com.my.recipe.model.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository
    extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {
  Optional<Users> findByUsernameIgnoreCase(String username);
}
