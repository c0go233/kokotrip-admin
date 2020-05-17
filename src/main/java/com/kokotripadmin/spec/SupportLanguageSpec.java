package com.kokotripadmin.spec;

import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.common.SupportLanguage_;
import org.springframework.data.jpa.domain.Specification;

public class SupportLanguageSpec {

    public static Specification<SupportLanguage> findByName(String supportLanguageName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(SupportLanguage_.name), supportLanguageName);
        };
    }

    public static Specification<SupportLanguage> findByDisplayName(String supportLanguageDisplayName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(SupportLanguage_.displayName), supportLanguageDisplayName);
        };
    }


}
