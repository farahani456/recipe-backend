package com.my.recipe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "recipe_images")
public class RecipeImages {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recipe_image_id")
  private Long recipeImageId;

  @Column(name = "recipe_id")
  private Long recipeId;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "content_type", nullable = false)
  private String contentType;

  @Lob
  @Column(name = "image_data", columnDefinition = "BYTEA")
  @JdbcTypeCode(SqlTypes.BINARY)
  private byte[] imageData;

  @Column(name = "created_by", nullable = false, updatable = false)
  private Long createdBy;

  @Column(name = "updated_by", nullable = false)
  private Long updatedBy;

  @Column(name = "created_date", nullable = false, updatable = false)
  private LocalDateTime createdDate;

  @Column(name = "updated_date", nullable = false)
  private LocalDateTime updatedDate;

  @Column(name = "is_deleted")
  private Boolean isDeleted;

  @Column(name = "deleted_by")
  private Long deletedBy;

  @Column(name = "deleted_date")
  private LocalDateTime deletedDate;

  @Column(name = "is_active")
  private Boolean isActive;
}
