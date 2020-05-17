package com.kokotripadmin.spec;

import com.kokotripadmin.entity.city.CityThemeRel_;
import com.kokotripadmin.entity.photozone.PhotoZone;
import com.kokotripadmin.entity.photozone.PhotoZoneInfo;
import com.kokotripadmin.entity.photozone.PhotoZoneInfo_;
import com.kokotripadmin.entity.photozone.PhotoZone_;
import com.kokotripadmin.entity.tourspot.TourSpotInfo;
import com.kokotripadmin.entity.tourspot.TourSpotInfo_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

public class PhotoZoneSpec {


    public static Specification<PhotoZone> findById(Integer photoZoneId, boolean fetchInfo) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (fetchInfo) {
                root.fetch(PhotoZone_.photoZoneInfoList.getName(), JoinType.LEFT);
                criteriaQuery.distinct(true);
            }
            return criteriaBuilder.equal(root.get(PhotoZone_.id), photoZoneId);
        };
    }

    public static Specification<PhotoZone> findByParentTourSpotId(Integer parentTourSpotId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(PhotoZone_.parentTourSpotId), parentTourSpotId);
        };
    }

    public static Specification<PhotoZone> findByTourSpotId(Integer tourSpotId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(PhotoZone_.tourSpotId), tourSpotId);
        };
    }

    public static Specification<PhotoZone> findByActivityId(Integer activityId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(PhotoZone_.activityId), activityId);
        };
    }

    private static Specification<PhotoZone> findByName(String photoZoneName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(PhotoZone_.name), photoZoneName);
        };
    }

    public static Specification<PhotoZone> findByParentTourSpotIdAndName(Integer parentTourSpotId, String photoZoneName) {
        return Specification.where(findByParentTourSpotId(parentTourSpotId)).and(findByName(photoZoneName));
    }

    public static Specification<PhotoZone> findByParentTourSpotIdAndTourSpotId(Integer parentTourSpotId, Integer tourSpotId) {
        return Specification.where(findByParentTourSpotId(parentTourSpotId)).and(findByTourSpotId(tourSpotId));
    }

    public static Specification<PhotoZone> findByParentTourSpotIdAndActivityId(Integer parentTourSpotId, Integer activityId) {
        return Specification.where(findByParentTourSpotId(parentTourSpotId)).and(findByActivityId(activityId));
    }


    public static Specification<PhotoZoneInfo> findInfoById(Integer photoZoneId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(PhotoZoneInfo_.photoZoneId), photoZoneId);
        };
    }

    public static Specification<PhotoZoneInfo> findInfoBySupportLanguageId(Integer supportLanguageId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(PhotoZoneInfo_.supportLanguageId), supportLanguageId);
        };
    }

    public static Specification<PhotoZoneInfo> findInfoByIdAndSupportLanguageId(Integer photoZoneId, Integer supportLanguageId) {
        return Specification.where(findInfoById(photoZoneId)).and(findInfoBySupportLanguageId(supportLanguageId));
    }
}
