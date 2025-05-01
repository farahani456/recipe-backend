package com.my.recipe.repository.specification;

import com.my.recipe.model.Uoms;
import com.my.recipe.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

public class UomSpecifications {
  public static Specification<Uoms> search(String keyword, Boolean isActive) {
    String processedKeyword = !Utils.isExist(keyword) ? "" : keyword.toLowerCase();
    String likePattern = "%" + processedKeyword + "%";
    return (root, query, cb) -> {
      Specification<Uoms> specification =
          (root1, query1, cb1) ->
              cb.or(
                  cb.like(cb1.lower(root1.get("uomName")), likePattern),
                  cb.like(cb1.lower(root1.get("uomCode")), likePattern),
                  cb.like(cb1.lower(root1.get("description")), likePattern));

      if (isActive != null) {
        specification =
            specification.and((root1, query1, cb1) -> cb1.equal(root1.get("isActive"), isActive));
      }

      return specification.toPredicate(root, query, cb);
    };
  }
}
