package com.kokotripadmin.dao.interfaces.tourspot;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicketDescriptionInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface TourSpotTicketDescriptionInfoDao extends GenericDao<TourSpotTicketDescriptionInfo, Integer>, JpaSpecificationExecutor<TourSpotTicketDescriptionInfo> {
}
