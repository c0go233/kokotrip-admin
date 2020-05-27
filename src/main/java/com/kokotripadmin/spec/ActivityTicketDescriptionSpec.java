package com.kokotripadmin.spec;

import com.kokotripadmin.entity.activity.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

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





    public static Specification<ActivityTicketDescriptionImage> findImageById(Integer activityTicketDescriptionId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(ActivityTicketDescriptionImage_.activityTicketDescriptionId), activityTicketDescriptionId);
    }

    public static Specification<ActivityTicketDescriptionImage> findImageByImageBucketKey(String activityTicketDescriptionImageBucketKey) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(ActivityTicketDescriptionImage_.bucketKey),
                                                                               activityTicketDescriptionImageBucketKey);
    }

    public static Specification<ActivityTicketDescriptionImage> findImageByIdAndImageBucketKey(Integer activityTicketDescriptionId, String activityTicketDescriptionImageBucketKey) {
        return Specification.where(findImageById(activityTicketDescriptionId)).and(findImageByImageBucketKey(activityTicketDescriptionImageBucketKey));
    }

    public static Specification<ActivityTicketDescriptionImage> findImageByIds(final List<Integer> imageIdList) {
        return (root, criteriaQuery, criteriaBuilder) -> root.get(ActivityTicketDescriptionImage_.id).in(imageIdList);
    }


}
