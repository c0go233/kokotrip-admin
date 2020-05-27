package com.kokotripadmin.spec;

import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.entity.activity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

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

    public static Specification<Activity> findByNameLike(String activityName) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get(Activity_.name), "%" + activityName + "%");
    }



    public static Specification<ActivityImage> findImageById(Integer activityId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(ActivityImage_.activityId), activityId);
    }

    public static Specification<ActivityImage> findImageByImageBucketKey(String activityImageBucketKey) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(ActivityImage_.bucketKey),
                                                                               activityImageBucketKey);
    }

    public static Specification<ActivityImage> findImageByIdAndImageBucketKey(Integer activityId, String activityImageBucketKey) {
        return Specification.where(findImageById(activityId)).and(findImageByImageBucketKey(activityImageBucketKey));
    }

    public static Specification<ActivityImage> findImageByRepImage(boolean repImage) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(ActivityImage_.repImage), repImage);
    }

    public static Specification<ActivityImage> findImageByIdAndRepImage(Integer activityId, boolean repImage) {
        return Specification.where(findImageById(activityId)).and(findImageByRepImage(repImage));
    }

    public static Specification<ActivityImage> findImageByIds(final List<Integer> imageIdList) {
        return (root, criteriaQuery, criteriaBuilder) -> root.get(ActivityImage_.id).in(imageIdList);
    }



}
