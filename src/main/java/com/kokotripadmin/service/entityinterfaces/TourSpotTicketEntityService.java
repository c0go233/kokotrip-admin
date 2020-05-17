package com.kokotripadmin.service.entityinterfaces;

import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicket;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicketInfo;
import com.kokotripadmin.exception.tour_spot.ticket.TourSpotTicketInfoNotFoundException;
import com.kokotripadmin.exception.tour_spot.ticket.TourSpotTicketNotFoundException;

public interface TourSpotTicketEntityService {
    TourSpotTicket findEntityById(Integer tourSpotTicketId) throws TourSpotTicketNotFoundException;
    TourSpotTicketInfo findInfoEntityByIdAndSupportLanguageId(Integer tourSpotTicketId, Integer supportLanguageId) throws
            TourSpotTicketInfoNotFoundException;
}
