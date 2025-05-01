package com.my.recipe.repository;

import com.my.recipe.model.Uoms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UomRepository extends JpaRepository<Uoms, Long>, JpaSpecificationExecutor<Uoms> {}
