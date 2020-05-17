package com.kokotripadmin.service.entityinterfaces;

import com.kokotripadmin.entity.city.City;
import com.kokotripadmin.entity.tourspot.TourSpot;
import com.kokotripadmin.entity.tourspot.TourSpotInfo;
import com.kokotripadmin.exception.tour_spot.TourSpotInfoNotFoundException;
import com.kokotripadmin.exception.tour_spot.TourSpotNotFoundException;

import java.util.List;

public interface TourSpotEntityService {
    TourSpot findEntityById(Integer tourSpotId) throws TourSpotNotFoundException;
    TourSpotInfo findInfoEntityByIdAndSupportLanguageId(Integer tourSpotId, Integer supportLanguageId)
    throws TourSpotInfoNotFoundException;
    List<TourSpotInfo> findAllInfoById(Integer tourSpotId);

}
