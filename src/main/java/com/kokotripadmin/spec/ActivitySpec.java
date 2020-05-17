package com.kokotripadmin.spec;

import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.entity.activity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public class ActivitySpec {

    public static Specification<ActivityInfo> findInfoById(Integer activityId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ActivityInfo_.activityId), activityId);
        };
    }

    public static Specification<ActivityInfo> findInfoBySupportLanguageId(Integer supportLanguageId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ActivityInfo_.supportLanguageId), supportLanguageId);
        };
    }

    public static Specification<ActivityInfo> findInfoByIdAndSupportLanguageId(Integer activityId, Integer supportLanguageId) {
        return Specification.where(findInfoById(activityId)).and(findInfoBySupportLanguageId(supportLanguageId));
    }


    public static Specification<Activity> findByTourSpotId(Integer tourSpotId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Activity_.tourSpotId), tourSpotId);
    }

    public static Specification<Activity> findByName(String activityName) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Activity_.name), activityName);
    }

    public static Specification<Activity> findByTourSpotIdAndName(Integer tourSpotId, String activityName) {
        return Specification.where(findByTourSpotId(tourSpotId)).and(findByName(activityName));
    }


    public static Specification<ActivityDescriptionInfo> findActivityDescriptionInfoByActivityDescriptionId(int activityDescriptionId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ActivityDescriptionInfo_.activityDescriptionId), activityDescriptionId);
        };
    }

    public static Specification<ActivityDescriptionInfo> findActivityDescriptionInfoBySupportLanguageId(int supportLanguageId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ActivityDescriptionInfo_.supportLanguageId), supportLanguageId);
        };
    }

    public static Specification<ActivityDescriptionInfo> findDefaultActivityDescriptionInfo(int activityDescriptionId) {
        return Specification.where(findActivityDescriptionInfoByActivityDescriptionId(activityDescriptionId))
                            .and(findActivityDescriptionInfoBySupportLanguageId(SupportLanguageEnum.Korean.getId()));
    }


    public static Specification<Activity> findByNameLike(String activityName) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get(Activity_.name), "%" + activityName + "%");
    }
}
