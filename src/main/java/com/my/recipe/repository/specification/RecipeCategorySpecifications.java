package com.my.recipe.repository.specification;

import com.my.recipe.model.RecipeCategories;
import com.my.recipe.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

public class RecipeCategorySpecifications {
  public static Specification<RecipeCategories> search(String keyword, Boolean isActive) {
    String processedKeyword = !Utils.isExist(keyword) ? "" : keyword.toLowerCase();
    String likePattern = "%" + processedKeyword + "%";
    return (root, query, cb) -> {
      Specification<RecipeCategories> specification =
          (root1, query1, cb1) ->
              cb.or(
                  cb.like(cb1.lower(root1.get("categoryName")), likePattern),
                  cb.like(cb1.lower(root1.get("description")), likePattern));

      if (isActive != null) {
        specification =
            specification.and((root1, query1, cb1) -> cb1.equal(root1.get("isActive"), isActive));
      }

      return specification.toPredicate(root, query, cb);
    };
  }
}
