package com.my.recipe.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
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
