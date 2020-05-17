package com.kokotripadmin.service.interfaces.tourspot;


import com.kokotripadmin.dto.tourspot.TourSpotTicketDto;
import com.kokotripadmin.dto.tourspot.TourSpotTicketInfoDto;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.ticket.TicketTypeNotFoundException;
import com.kokotripadmin.exception.tour_spot.*;
import com.kokotripadmin.exception.tour_spot.ticket.*;

public interface TourSpotTicketService {

    Integer save(TourSpotTicketDto tourSpotTicketDto)
    throws TourSpotTicketNotFoundException, SupportLanguageNotFoundException,
           TourSpotNotFoundException, TourSpotTicketNameDuplicateException, TourSpotInfoNotFoundException,
           TicketTypeNotFoundException, TourSpotTicketInfoAlreadyExistsException, TourSpotTicketInfoNotFoundException;

    TourSpotTicketInfoDto saveInfo(TourSpotTicketInfoDto tourSpotInfoDto)
    throws TourSpotTicketNotFoundException, TourSpotInfoNotFoundException, SupportLanguageNotFoundException,
           TourSpotTicketInfoAlreadyExistsException, TourSpotTicketInfoNotEditableException,
           TourSpotTicketInfoNotFoundException;
    Integer delete(Integer tourSpotTicketId) throws TourSpotTicketNotFoundException;
    void deleteInfo(Integer tourSpotTicketInfoId)
    throws TourSpotTicketInfoNotFoundException, TourSpotTicketInfoNotDeletableException;

    TourSpotTicketDto findById(Integer tourSpotTicketId) throws TourSpotTicketNotFoundException;

    TourSpotTicketDto findByIdInDetail(Integer tourSpotTicketId) throws TourSpotTicketNotFoundException;
}
