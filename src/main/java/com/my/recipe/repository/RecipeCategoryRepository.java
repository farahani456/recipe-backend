package com.my.recipe.repository;

import com.my.recipe.model.RecipeCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RecipeCategoryRepository
    extends JpaRepository<RecipeCategories, Long>, JpaSpecificationExecutor<RecipeCategories> {}
