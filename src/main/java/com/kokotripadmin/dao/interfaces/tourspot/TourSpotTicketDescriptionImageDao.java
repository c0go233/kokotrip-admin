package com.kokotripadmin.dao.interfaces.tourspot;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicketDescriptionImage;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TourSpotTicketDescriptionImageDao extends GenericDao<TourSpotTicketDescriptionImage, Integer>,
                                                           JpaSpecificationExecutor<TourSpotTicketDescriptionImage> {
}
