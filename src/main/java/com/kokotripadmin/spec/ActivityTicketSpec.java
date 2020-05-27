package com.kokotripadmin.spec;

import com.kokotripadmin.entity.activity.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

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




    public static Specification<ActivityTicketImage> findImageById(Integer activityTicketId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(ActivityTicketImage_.activityTicketId), activityTicketId);
    }

    public static Specification<ActivityTicketImage> findImageByImageBucketKey(String activityTicketImageBucketKey) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(ActivityTicketImage_.bucketKey),
                                                                               activityTicketImageBucketKey);
    }

    public static Specification<ActivityTicketImage> findImageByIdAndImageBucketKey(Integer activityTicketId, String activityTicketImageBucketKey) {
        return Specification.where(findImageById(activityTicketId)).and(findImageByImageBucketKey(activityTicketImageBucketKey));
    }

    public static Specification<ActivityTicketImage> findImageByRepImage(boolean repImage) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(ActivityTicketImage_.repImage), repImage);
    }

    public static Specification<ActivityTicketImage> findImageByIdAndRepImage(Integer activityTicketId, boolean repImage) {
        return Specification.where(findImageById(activityTicketId)).and(findImageByRepImage(repImage));
    }

    public static Specification<ActivityTicketImage> findImageByIds(final List<Integer> imageIdList) {
        return (root, criteriaQuery, criteriaBuilder) -> root.get(ActivityTicketImage_.id).in(imageIdList);
    }


}
