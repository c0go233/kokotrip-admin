package com.kokotripadmin.dao.interfaces.tourspot;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicketDescription;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TourSpotTicketDescriptionDao extends GenericDao<TourSpotTicketDescription, Integer>, JpaSpecificationExecutor<TourSpotTicketDescription> {
}
