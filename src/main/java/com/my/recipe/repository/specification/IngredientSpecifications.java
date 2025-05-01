package com.my.recipe.repository.specification;

import com.my.recipe.dto.ingredients.IngredientSearchDTO;
import com.my.recipe.model.Ingredients;
import com.my.recipe.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

public class IngredientSpecifications {
  public static Specification<Ingredients> search(IngredientSearchDTO searchDTO) {
    String processedKeyword =
        !Utils.isExist(searchDTO.getSearch()) ? "" : searchDTO.getSearch().toLowerCase();
    String likePattern = "%" + processedKeyword + "%";
    return (root, query, cb) -> {
      Specification<Ingredients> specification =
          (root1, query1, cb1) ->
              cb.or(
                  cb.like(cb1.lower(root1.get("ingredientName")), likePattern),
                  cb.like(cb1.lower(root1.get("description")), likePattern));

      if (searchDTO.getIsActive() != null) {
        specification =
            specification.and(
                (root1, query1, cb1) -> cb1.equal(root1.get("isActive"), searchDTO.getIsActive()));
      }

      if (searchDTO.getUomId() != null) {
        specification =
            specification.and(
                (root1, query1, cb1) -> cb1.equal(root1.get("uomId"), searchDTO.getUomId()));
      }

      return specification.toPredicate(root, query, cb);
    };
  }
}
