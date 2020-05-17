package com.kokotripadmin.spec.tourspot;

import com.kokotripadmin.entity.tourspot.TourSpotDescription;
import com.kokotripadmin.entity.tourspot.TourSpotDescriptionInfo;
import com.kokotripadmin.entity.tourspot.TourSpotDescription_;
import com.kokotripadmin.entity.tourspot.TourSpotDescriptionInfo_;
import org.springframework.data.jpa.domain.Specification;

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
}
