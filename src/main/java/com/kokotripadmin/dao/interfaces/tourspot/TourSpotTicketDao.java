package com.kokotripadmin.dao.interfaces.tourspot;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicket;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TourSpotTicketDao extends GenericDao<TourSpotTicket, Integer>, JpaSpecificationExecutor<TourSpotTicket> {
}
