package com.kokotripadmin.dao.interfaces.tourspot;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.tourspot.TourSpot;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TourSpotDao extends GenericDao<TourSpot, Integer>, JpaSpecificationExecutor<TourSpot> {


}
