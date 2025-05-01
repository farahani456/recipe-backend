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
@EqualsAndHashCode(callSuper = true)
@Entity(name = "ingredients")
public class Ingredients extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ingredient_id")
  private Long ingredientId;

  @Column(name = "ingredient_name", nullable = false)
  private String ingredientName;

  @Column(name = "description")
  private String description;

  @Column(name = "uom_id", nullable = false)
  private Long uomId;
}
