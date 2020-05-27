package com.kokotripadmin.spec.tourspot;

import com.kokotripadmin.entity.tourspot.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class TourSpotDescriptionSpec {

    public static Specification<TourSpotDescriptionInfo> findInfoById(Integer tourSpotDescriptionId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TourSpotDescriptionInfo_.tourSpotDescriptionId), tourSpotDescriptionId);
        };
    }

    public static Specification<TourSpotDescriptionInfo> findInfoBySupportLanguageId(Integer supportLanguageId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TourSpotDescriptionInfo_.supportLanguageId), supportLanguageId);
        };
    }

    public static Specification<TourSpotDescriptionInfo> findInfoByIdAndSupportLanguageId(Integer tourSpotDescriptionId,
                                                                                          Integer supportLanguageId) {
        return Specification.where(findInfoById(tourSpotDescriptionId)).and(findInfoBySupportLanguageId(supportLanguageId));
    }

    public static Specification<TourSpotDescription> findByTourSpotId(Integer tourSpotId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TourSpotDescription_.tourSpotId), tourSpotId);
        };
    }

    public static Specification<TourSpotDescription> findByName(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TourSpotDescription_.name), name);
        };
    }

    public static Specification<TourSpotDescription> findByTourSpotIdAndName(Integer tourSpotId, String name) {
        return Specification.where(findByTourSpotId(tourSpotId)).and(findByName(name));
    }



    public static Specification<TourSpotDescriptionImage> findImageById(Integer tourSpotDescriptionId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(TourSpotDescriptionImage_.tourSpotDescriptionId),
                                                                               tourSpotDescriptionId);
    }

    public static Specification<TourSpotDescriptionImage> findImageByImageBucketKey(String tourSpotDescriptionImageBucketKey) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(TourSpotDescriptionImage_.bucketKey),
                                                                               tourSpotDescriptionImageBucketKey);
    }

    public static Specification<TourSpotDescriptionImage> findImageByIdAndImageBucketKey(Integer tourSpotDescriptionId, String tourSpotDescriptionImageBucketKey) {
        return Specification.where(findImageById(tourSpotDescriptionId)).and(findImageByImageBucketKey(tourSpotDescriptionImageBucketKey));
    }

    public static Specification<TourSpotDescriptionImage> findImageByIds(final List<Integer> imageIdList) {
        return (root, criteriaQuery, criteriaBuilder) -> root.get(TourSpotDescriptionImage_.id).in(imageIdList);
    }

}
