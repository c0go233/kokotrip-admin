package com.kokotripadmin.dao.interfaces.tourspot;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicketInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface TourSpotTicketInfoDao extends GenericDao<TourSpotTicketInfo, Integer>, JpaSpecificationExecutor<TourSpotTicketInfo> {
}
