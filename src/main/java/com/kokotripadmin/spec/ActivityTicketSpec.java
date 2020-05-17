package com.kokotripadmin.spec;

import com.kokotripadmin.entity.activity.ActivityTicket;
import com.kokotripadmin.entity.activity.ActivityTicketInfo;
import com.kokotripadmin.entity.activity.ActivityTicketInfo_;
import com.kokotripadmin.entity.activity.ActivityTicket_;
import org.springframework.data.jpa.domain.Specification;

public class ActivityTicketSpec {


    public static Specification<ActivityTicket> findByActivityId(Integer activityId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ActivityTicket_.activityId), activityId);
        };
    }

    public static Specification<ActivityTicket> findByName(String activityTicketName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ActivityTicket_.name), activityTicketName);
        };
    }

    public static Specification<ActivityTicket> findByActivityIdAndName(Integer activityId, String activityTicketName) {
        return Specification.where(findByActivityId(activityId)).and(findByName(activityTicketName));
    }


    public static Specification<ActivityTicketInfo> findInfoById(Integer activityTicketId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ActivityTicketInfo_.activityTicketId), activityTicketId);
        };
    }

    public static Specification<ActivityTicketInfo> findInfoBySupportLanguageId(Integer supportLanguageId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(ActivityTicketInfo_.supportLanguageId), supportLanguageId);
        };
    }

    public static Specification<ActivityTicketInfo> findInfoByIdAndSupportLanguageId(Integer activityTicketId,
                                                                                     Integer supportLanguageId) {
        return Specification.where(findInfoById(activityTicketId)).and(findInfoBySupportLanguageId(supportLanguageId));
    }

}
