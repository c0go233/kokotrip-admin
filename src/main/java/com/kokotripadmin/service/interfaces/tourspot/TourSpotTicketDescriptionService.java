package com.kokotripadmin.service.interfaces.tourspot;

import com.kokotripadmin.dto.tourspot.TourSpotTicketDescriptionDto;
import com.kokotripadmin.dto.tourspot.TourSpotTicketDescriptionImageDto;
import com.kokotripadmin.dto.tourspot.TourSpotTicketDescriptionInfoDto;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tour_spot.ticket.TourSpotTicketDescriptionInfoNotDeletableException;
import com.kokotripadmin.exception.tour_spot.ticket.TourSpotTicketDescriptionInfoNotEditableException;
import com.kokotripadmin.exception.tour_spot.ticket.*;

import java.io.IOException;
import java.util.List;

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






    void deleteImage(Integer imageId) throws TourSpotTicketDescriptionImageNotFoundException;
    Integer saveImage(TourSpotTicketDescriptionImageDto tourSpotTicketDescriptionImageDto)
    throws TourSpotTicketDescriptionNotFoundException, ImageDuplicateException, IOException, FileIsNotImageException;
    void updateImageOrder(List<Integer> imageIdList);
}
