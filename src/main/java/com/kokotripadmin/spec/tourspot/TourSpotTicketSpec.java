package com.kokotripadmin.spec.tourspot;

import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.entity.tourspot.ticket.*;
//import com.kokotripadmin.entity.tourspot.TourSpotTicketDescriptionInfo_;
//import com.kokotripadmin.entity.tourspot.TourSpotTicketInfo_;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class TourSpotTicketSpec {




    public static Specification<TourSpotTicket> findByTourSpotId(Integer tourSpotId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TourSpotTicket_.tourSpotId), tourSpotId);
        };
    }

    public static Specification<TourSpotTicket> findByName(String tourSpotTicketName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TourSpotTicket_.name), tourSpotTicketName);
        };
    }

    public static Specification<TourSpotTicket> findByTourSpotIdAndName(Integer tourSpotId, String tourSpotTicketName) {
        return Specification.where(findByTourSpotId(tourSpotId)).and(findByName(tourSpotTicketName));
    }


    public static Specification<TourSpotTicketInfo> findInfoById(Integer tourSpotTicketId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TourSpotTicketInfo_.tourSpotTicketId), tourSpotTicketId);
        };
    }

    public static Specification<TourSpotTicketInfo> findInfoBySupportLanguageId(Integer supportLanguageId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TourSpotTicketInfo_.supportLanguageId), supportLanguageId);
        };
    }

    public static Specification<TourSpotTicketInfo> findInfoByIdAndSupportLanguageId(Integer tourSpotTicketId,
                                                                                     Integer supportLanguageId) {
        return Specification.where(findInfoById(tourSpotTicketId)).and(findInfoBySupportLanguageId(supportLanguageId));
    }





    public static Specification<TourSpotTicketImage> findImageById(Integer tourSpotTicketId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(TourSpotTicketImage_.tourSpotTicketId),
                                                                               tourSpotTicketId);
    }

    public static Specification<TourSpotTicketImage> findImageByImageBucketKey(String tourSpotTicketImageBucketKey) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(TourSpotTicketImage_.bucketKey),
                                                                               tourSpotTicketImageBucketKey);
    }

    public static Specification<TourSpotTicketImage> findImageByIdAndImageBucketKey(Integer tourSpotTicketId,
                                                                                    String tourSpotTicketImageBucketKey) {
        return Specification.where(findImageById(tourSpotTicketId)).and(findImageByImageBucketKey(tourSpotTicketImageBucketKey));
    }

    public static Specification<TourSpotTicketImage> findImageByRepImage(boolean repImage) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(TourSpotTicketImage_.repImage), repImage);
    }

    public static Specification<TourSpotTicketImage> findImageByIdAndRepImage(Integer tourSpotTicketId, boolean repImage) {
        return Specification.where(findImageById(tourSpotTicketId)).and(findImageByRepImage(repImage));
    }

    public static Specification<TourSpotTicketImage> findImageByIds(final List<Integer> imageIdList) {
        return (root, criteriaQuery, criteriaBuilder) -> root.get(TourSpotTicketImage_.id).in(imageIdList);
    }


}
