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
@Entity(name = "uoms")
public class Uoms extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "uom_id")
  private Long uomId;

  @Column(name = "uom_name", nullable = false)
  private String uomName;

  @Column(name = "uom_code", nullable = false, unique = true)
  private String uomCode;

  @Column(name = "description", nullable = false)
  private String description;
}
