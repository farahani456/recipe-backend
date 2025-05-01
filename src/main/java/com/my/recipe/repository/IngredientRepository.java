package com.my.recipe.repository;

import com.my.recipe.model.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IngredientRepository
    extends JpaRepository<Ingredients, Long>, JpaSpecificationExecutor<Ingredients> {}
