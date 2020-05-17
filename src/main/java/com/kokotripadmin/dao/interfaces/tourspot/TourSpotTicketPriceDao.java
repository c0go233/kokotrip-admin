package com.kokotripadmin.dao.interfaces.tourspot;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicketPrice;
import org.springframework.stereotype.Repository;

@Repository
public interface TourSpotTicketPriceDao extends GenericDao<TourSpotTicketPrice, Integer> {
}
