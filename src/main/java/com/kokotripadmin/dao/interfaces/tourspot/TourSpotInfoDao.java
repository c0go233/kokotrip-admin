package com.kokotripadmin.dao.interfaces.tourspot;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.tourspot.TourSpotInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TourSpotInfoDao extends GenericDao<TourSpotInfo, Integer>, JpaSpecificationExecutor<TourSpotInfo> {
}
