package com.my.recipe.repository;

import com.my.recipe.model.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RecipeRepository
    extends JpaRepository<Recipes, Long>, JpaSpecificationExecutor<Recipes> {}
