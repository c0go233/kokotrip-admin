package com.kokotripadmin.service.interfaces.tourspot;

import com.kokotripadmin.dto.tourspot.TourSpotTicketDescriptionDto;
import com.kokotripadmin.dto.tourspot.TourSpotTicketDescriptionInfoDto;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tour_spot.ticket.TourSpotTicketDescriptionInfoNotDeletableException;
import com.kokotripadmin.exception.tour_spot.ticket.TourSpotTicketDescriptionInfoNotEditableException;
import com.kokotripadmin.exception.tour_spot.ticket.*;

public interface TourSpotTicketDescriptionService {

    TourSpotTicketDescriptionDto findById(Integer tourSpotTicketDescriptionId)
    throws TourSpotTicketDescriptionNotFoundException;
    Integer save(TourSpotTicketDescriptionDto tourSpotTicketDescriptionDto)
    throws TourSpotTicketDescriptionNotFoundException,
            TourSpotTicketDescriptionAlreadyExistsException, SupportLanguageNotFoundException,
            TourSpotTicketDescriptionInfoAlreadyExistsException, TourSpotTicketDescriptionInfoNotFoundException,
            TourSpotTicketNotFoundException, TourSpotTicketInfoNotFoundException;

    TourSpotTicketDescriptionDto findByIdInDetail(Integer tourSpotTicketDescriptionId)
    throws TourSpotTicketDescriptionNotFoundException;
    TourSpotTicketDescriptionInfoDto saveInfo(TourSpotTicketDescriptionInfoDto tourSpotTicketDescriptionInfoDto)
    throws SupportLanguageNotFoundException, TourSpotTicketDescriptionInfoAlreadyExistsException, TourSpotTicketDescriptionNotFoundException,
            TourSpotTicketDescriptionInfoNotEditableException, TourSpotTicketDescriptionInfoNotFoundException,
            TourSpotTicketInfoNotFoundException;

    Integer delete(Integer tourSpotTicketDescriptionId) throws TourSpotTicketDescriptionNotFoundException;
    void deleteInfo(Integer tourSpotTicketDescriptionInfoId)
    throws TourSpotTicketDescriptionInfoNotFoundException, TourSpotTicketDescriptionInfoNotDeletableException;
}
