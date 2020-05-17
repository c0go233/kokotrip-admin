package com.kokotripadmin.spec;

import com.kokotripadmin.entity.activity.ActivityDescription;
import com.kokotripadmin.entity.activity.ActivityDescriptionInfo;
import com.kokotripadmin.entity.activity.ActivityDescriptionInfo_;
import com.kokotripadmin.entity.activity.ActivityDescription_;
import org.springframework.data.jpa.domain.Specification;

public class ActivityDescriptionSpec {

    public static Specification<ActivityDescriptionInfo> findInfoById(Integer activityDescriptionId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ActivityDescriptionInfo_.activityDescriptionId), activityDescriptionId);
        };
    }

    public static Specification<ActivityDescriptionInfo> findInfoBySupportLanguageId(Integer supportLanguageId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ActivityDescriptionInfo_.supportLanguageId), supportLanguageId);
        };
    }

    public static Specification<ActivityDescriptionInfo> findInfoByIdAndSupportLanguageId(Integer activityDescriptionId,
                                                                                          Integer supportLanguageId) {
        return Specification.where(findInfoById(activityDescriptionId)).and(findInfoBySupportLanguageId(supportLanguageId));
    }

    public static Specification<ActivityDescription> findByActivityId(Integer activityId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ActivityDescription_.activityId), activityId);
        };
    }

    public static Specification<ActivityDescription> findByName(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ActivityDescription_.name), name);
        };
    }

    public static Specification<ActivityDescription> findByActivityIdAndName(Integer activityId, String name) {
        return Specification.where(findByActivityId(activityId)).and(findByName(name));
    }


}
