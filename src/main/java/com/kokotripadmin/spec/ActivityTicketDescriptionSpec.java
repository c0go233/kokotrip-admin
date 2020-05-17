package com.kokotripadmin.spec;

import com.kokotripadmin.entity.activity.ActivityTicketDescription;
import com.kokotripadmin.entity.activity.ActivityTicketDescriptionInfo;
import com.kokotripadmin.entity.activity.ActivityTicketDescriptionInfo_;
import com.kokotripadmin.entity.activity.ActivityTicketDescription_;
import org.springframework.data.jpa.domain.Specification;

public class ActivityTicketDescriptionSpec {


    public static Specification<ActivityTicketDescriptionInfo> findInfoById(Integer activityTicketDescriptionId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ActivityTicketDescriptionInfo_.activityTicketDescriptionId), activityTicketDescriptionId);
        };
    }

    public static Specification<ActivityTicketDescriptionInfo> findInfoBySupportLanguageId(Integer supportLanguageId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ActivityTicketDescriptionInfo_.supportLanguageId), supportLanguageId);
        };
    }

    public static Specification<ActivityTicketDescriptionInfo> findInfoByIdAndSupportLanguageId(Integer activityTicketDescriptionId,
                                                                                                Integer supportLanguageId) {
        return Specification.where(findInfoById(activityTicketDescriptionId)).and(findInfoBySupportLanguageId(supportLanguageId));
    }

    public static Specification<ActivityTicketDescription> findByActivityTicketId(Integer activityTicketId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ActivityTicketDescription_.activityTicketId), activityTicketId);
        };
    }

    public static Specification<ActivityTicketDescription> findByName(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ActivityTicketDescription_.name), name);
        };
    }

    public static Specification<ActivityTicketDescription> findByActivityTicketIdAndName(Integer activityTicketId, String name) {
        return Specification.where(findByActivityTicketId(activityTicketId)).and(findByName(name));
    }


}
