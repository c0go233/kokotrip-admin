package com.kokotripadmin.spec;

import com.kokotripadmin.entity.tourspot.TourSpot_;
import com.kokotripadmin.entity.tourspot.TourSpotTradingHour;
import com.kokotripadmin.entity.tourspot.TourSpotTradingHour_;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class TourSpotTradingHourSpec {

    public static Specification<TourSpotTradingHour> findAllByNotInIds(List<Integer> ids) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.not(root.get(TourSpotTradingHour_.id).in(ids));
        };
    }

    public static Specification<TourSpotTradingHour> findAllByTourSpotId(int tourSpotId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TourSpotTradingHour_.tourSpot).get(TourSpot_.id), tourSpotId);
        };
    }

    public static Specification<TourSpotTradingHour> findAllByTourSpotIdAndNotInIds(int tourSpotId, List<Integer> ids) {
        return Specification.where(findAllByTourSpotId(tourSpotId)).and(findAllByNotInIds(ids));
    }
}
