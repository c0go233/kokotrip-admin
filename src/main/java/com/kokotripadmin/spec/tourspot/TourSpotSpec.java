package com.kokotripadmin.spec.tourspot;

import com.kokotripadmin.entity.tourspot.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import java.util.List;

public class TourSpotSpec {



    public static Specification<TourSpot> findByName(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TourSpot_.name), name);
        };
    }

    public static Specification<TourSpot> findByRegionId(Integer regionId, boolean fetchInfo) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (fetchInfo) {
                root.fetch(TourSpot_.tourSpotInfoList.getName(), JoinType.LEFT);
                criteriaQuery.distinct(true);
            }

            return criteriaBuilder.equal(root.get(TourSpot_.regionId), regionId);
        };
    }

    public static Specification<TourSpotInfo> findInfoByTagInfoId(Integer tagInfoId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TourSpotInfo_.tagInfoId), tagInfoId);
        };
    }

    public static Specification<TourSpotInfo> findInfoById(Integer tourSpotId, boolean fetchTag) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (fetchTag) {
                root.fetch(TourSpotInfo_.tagInfo.getName(), JoinType.LEFT);
                criteriaQuery.distinct(true);
            }
            return criteriaBuilder.equal(root.get(TourSpotInfo_.tourSpotId), tourSpotId);
        };
    }

    public static Specification<TourSpotInfo> findInfoBySupportLanguageId(Integer supportLanguageId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TourSpotInfo_.supportLanguageId), supportLanguageId);
        };
    }

    public static Specification<TourSpotInfo> findInfoByIdAndSupportLanguageId(Integer tourSpotId, Integer supportLanguageId) {
        return Specification.where(findInfoById(tourSpotId, false)).and(findInfoBySupportLanguageId(supportLanguageId));
    }

    public static Specification<TourSpot> findByNameLike(String tourSpotName) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get(TourSpot_.name), "%" + tourSpotName + "%");
    }





    public static Specification<TourSpotImage> findImageById(Integer tourSpotId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(TourSpotImage_.tourSpotId), tourSpotId);
    }

    public static Specification<TourSpotImage> findImageByImageBucketKey(String tourSpotImageBucketKey) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(TourSpotImage_.bucketKey),
                                                                               tourSpotImageBucketKey);
    }

    public static Specification<TourSpotImage> findImageByIdAndImageBucketKey(Integer tourSpotId, String tourSpotImageBucketKey) {
        return Specification.where(findImageById(tourSpotId)).and(findImageByImageBucketKey(tourSpotImageBucketKey));
    }

    public static Specification<TourSpotImage> findImageByRepImage(boolean repImage) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(TourSpotImage_.repImage), repImage);
    }

    public static Specification<TourSpotImage> findImageByIdAndRepImage(Integer tourSpotId, boolean repImage) {
        return Specification.where(findImageById(tourSpotId)).and(findImageByRepImage(repImage));
    }

    public static Specification<TourSpotImage> findImageByIds(final List<Integer> imageIdList) {
        return (root, criteriaQuery, criteriaBuilder) -> root.get(TourSpotImage_.id).in(imageIdList);
    }



}
