package com.my.recipe.repository.specification;

import com.my.recipe.dto.recipes.RecipeSearchDTO;
import com.my.recipe.model.Recipes;
import com.my.recipe.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

public class RecipeSpecifications {
  public static Specification<Recipes> search(RecipeSearchDTO searchDTO) {
    String processedKeyword =
        !Utils.isExist(searchDTO.getSearch()) ? "" : searchDTO.getSearch().toLowerCase();
    String likePattern = "%" + processedKeyword + "%";
    return (root, query, cb) -> {
      Specification<Recipes> specification =
          (root1, query1, cb1) ->
              cb.or(
                  cb.like(cb1.lower(root1.get("title")), likePattern),
                  cb.like(cb1.lower(root1.get("description")), likePattern),
                  cb.like(cb1.lower(root1.get("instructions")), likePattern));

      if (searchDTO.getIsActive() != null) {
        specification =
            specification.and(
                (root1, query1, cb1) -> cb1.equal(root1.get("isActive"), searchDTO.getIsActive()));
      }

      if (searchDTO.getCategoryId() != null) {
        specification =
            specification.and(
                (root1, query1, cb1) ->
                    cb1.equal(root1.get("categoryId"), searchDTO.getCategoryId()));
      }

      if (searchDTO.getIsPublished() != null) {
        specification =
            specification.and(
                (root1, query1, cb1) ->
                    cb1.equal(root1.get("isPublished"), searchDTO.getIsPublished()));
      }

      return specification.toPredicate(root, query, cb);
    };
  }
}
