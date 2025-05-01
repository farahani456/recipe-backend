package com.my.recipe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "recipe_ingredients")
public class RecipeIngredients extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recipe_ingredient_id")
  private Long recipeIngredientId;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "ingredient_id", nullable = false)
  private Long ingredientId;

  @Column(name = "recipe_id", nullable = false)
  private Long recipeId;

  @Column(name = "quantity", nullable = false)
  private BigDecimal quantity;
}
