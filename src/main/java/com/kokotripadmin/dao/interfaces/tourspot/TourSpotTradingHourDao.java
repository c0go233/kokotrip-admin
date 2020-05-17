package com.kokotripadmin.dao.interfaces.tourspot;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.tourspot.TourSpotTradingHour;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TourSpotTradingHourDao extends GenericDao<TourSpotTradingHour, Integer>, JpaSpecificationExecutor<TourSpotTradingHour> {
}
