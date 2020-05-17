package com.kokotripadmin.spec;

import com.kokotripadmin.entity.tag.*;
import org.springframework.data.jpa.domain.Specification;

public class ThemeSpec {

    public static Specification<Theme> findByName(String themeName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Theme_.name), themeName);
        };
    }

    public static Specification<Theme> findByEnabled(boolean enabled) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Theme_.enabled), enabled);
        };
    }

    public static Specification<ThemeInfo> findInfoById(Integer themeId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ThemeInfo_.themeId), themeId);
        };
    }


    public static Specification<ThemeInfo> findInfoBySupportLanguageId(Integer supportLanguageId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ThemeInfo_.supportLanguageId), supportLanguageId);
        };
    }

    public static Specification<ThemeInfo> findInfoByIdAndSupportLanguageId(Integer themeId, Integer supportLanguageId) {
        return Specification.where(findInfoById(themeId)).and(findInfoBySupportLanguageId(supportLanguageId));
    }
}
