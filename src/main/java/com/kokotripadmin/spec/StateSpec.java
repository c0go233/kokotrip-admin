package com.kokotripadmin.spec;

import com.kokotripadmin.entity.State;
import com.kokotripadmin.entity.State_;
import org.springframework.data.jpa.domain.Specification;

public class StateSpec {



    public static Specification<State> findByEnabled(boolean stateEnabled) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(State_.createdAt)));
            return criteriaBuilder.equal(root.get(State_.enabled), stateEnabled);
        };
    }

    public static Specification<State> findByName(String stateName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(State_.name), stateName);
        };
    }
}
