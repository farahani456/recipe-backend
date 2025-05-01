package com.my.recipe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "recipes")
@EqualsAndHashCode(callSuper = true)
public class Recipes extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recipe_id")
  private Long recipeId;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "instructions", nullable = false)
  private String instructions;

  @Column(name = "category_id")
  private Long categoryId;

  @Column(name = "is_published", nullable = false)
  private Boolean isPublished;
}
