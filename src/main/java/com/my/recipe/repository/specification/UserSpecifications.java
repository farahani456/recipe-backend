package com.my.recipe.repository.specification;

import com.my.recipe.dto.users.UserSearchDTO;
import com.my.recipe.model.Users;
import com.my.recipe.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
  public static Specification<Users> search(UserSearchDTO searchDTO) {
    String processedKeyword =
        !Utils.isExist(searchDTO.getSearch()) ? "" : searchDTO.getSearch().toLowerCase();
    String likePattern = "%" + processedKeyword + "%";
    return (root, query, cb) -> {
      Specification<Users> specification =
          (root1, query1, cb1) ->
              cb.or(
                  cb.like(cb1.lower(root1.get("username")), likePattern),
                  cb.like(cb1.lower(root1.get("fullName")), likePattern));

      if (searchDTO.getIsActive() != null) {
        specification =
            specification.and(
                (root1, query1, cb1) -> cb1.equal(root1.get("isActive"), searchDTO.getIsActive()));
      }

      if (searchDTO.getIsAdmin() != null) {
        specification =
            specification.and(
                (root1, query1, cb1) -> cb1.equal(root1.get("isAdmin"), searchDTO.getIsAdmin()));
      }

      return specification.toPredicate(root, query, cb);
    };
  }
}
