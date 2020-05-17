package com.kokotripadmin.spec;

import com.kokotripadmin.entity.tag.*;

import org.springframework.data.jpa.domain.Specification;

public class TagSpec {


    public static Specification<Tag> findByName(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Tag_.name), name);
        };
    }

    public static Specification<TagInfo> findInfoById(Integer tagId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TagInfo_.tagId), tagId);
        };
    }

    public static Specification<TagInfo> findInfoBySupportLanguage(Integer supportLanguageId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TagInfo_.supportLanguageId), supportLanguageId);
        };
    }

    public static Specification<TagInfo> findInfoByIdAndSupportLanguageId(Integer tagId, Integer supportLanguageId) {
        return Specification.where(findInfoById(tagId)).and(findInfoBySupportLanguage(supportLanguageId));
    }





//    public static Specification<Tag> findByThemeEnabled(boolean themeEnabled) {
//        return (root, criteriaQuery, criteriaBuilder) -> {
//            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Tag_.id)));
//            return criteriaBuilder.equal(root.get(Tag_.theme).get(Theme_.enabled), themeEnabled);
//        };
//    }

//    public static Specification<Tag> findByEnabled(boolean tagEnabled) {
//        return (root, criteriaQuery, criteriaBuilder) -> {
//            return criteriaBuilder.equal(root.get(Tag_.enabled), tagEnabled);
//        };
//    }
//
//    public static Specification<Tag> findByEnabledAndThemeEnabled(boolean tagEnabled, boolean themeEnabled) {
//        return Specification.where(findByThemeEnabled(themeEnabled)).and(findByEnabled(tagEnabled));
//    }

}
