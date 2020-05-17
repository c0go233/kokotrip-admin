package com.kokotripadmin.spec.tourspot;

import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicketDescription;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicketDescriptionInfo;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicketDescriptionInfo_;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicketDescription_;
import org.springframework.data.jpa.domain.Specification;

public class TourSpotTicketDescriptionSpec {


    public static Specification<TourSpotTicketDescriptionInfo> findInfoById(Integer tourSpotTicketDescriptionId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TourSpotTicketDescriptionInfo_.tourSpotTicketDescriptionId), tourSpotTicketDescriptionId);
        };
    }

    public static Specification<TourSpotTicketDescriptionInfo> findInfoBySupportLanguageId(Integer supportLanguageId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TourSpotTicketDescriptionInfo_.supportLanguageId), supportLanguageId);
        };
    }

    public static Specification<TourSpotTicketDescriptionInfo> findInfoByIdAndSupportLanguageId(Integer tourSpotTicketDescriptionId,
                                                                                          Integer supportLanguageId) {
        return Specification.where(findInfoById(tourSpotTicketDescriptionId)).and(findInfoBySupportLanguageId(supportLanguageId));
    }

    public static Specification<TourSpotTicketDescription> findByTourSpotTicketId(Integer tourSpotTicketId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TourSpotTicketDescription_.tourSpotTicketId), tourSpotTicketId);
        };
    }

    public static Specification<TourSpotTicketDescription> findByName(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TourSpotTicketDescription_.name), name);
        };
    }

    public static Specification<TourSpotTicketDescription> findByTourSpotTicketIdAndName(Integer tourSpotTicketId, String name) {
        return Specification.where(findByTourSpotTicketId(tourSpotTicketId)).and(findByName(name));
    }


}
