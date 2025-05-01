package com.my.recipe.repository;

import com.my.recipe.model.RecipeIngredients;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RecipeIngredientRepository
    extends JpaRepository<RecipeIngredients, Long>, JpaSpecificationExecutor<RecipeIngredients> {
  List<RecipeIngredients> findByRecipeIdAndIsActiveTrue(Long recipeId);
}
