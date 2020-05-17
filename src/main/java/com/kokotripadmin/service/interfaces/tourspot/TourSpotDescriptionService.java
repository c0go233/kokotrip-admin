package com.kokotripadmin.service.interfaces.tourspot;

import com.kokotripadmin.dto.common.GenericInfoDto;
import com.kokotripadmin.dto.tourspot.TourSpotDescriptionDto;
import com.kokotripadmin.dto.tourspot.TourSpotDescriptionInfoDto;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tour_spot.*;

public interface TourSpotDescriptionService {

    TourSpotDescriptionDto findById(Integer tourSpotDescriptionId) throws TourSpotDescriptionNotFoundException;
    Integer save(TourSpotDescriptionDto tourSpotDescriptionDto)
    throws TourSpotNotFoundException, TourSpotInfoNotFoundException, TourSpotDescriptionNotFoundException,
           TourSpotDescriptionAlreadyExistsException, SupportLanguageNotFoundException,
           TourSpotDescriptionInfoAlreadyExistsException, TourSpotDescriptionInfoNotFoundException;
    TourSpotDescriptionInfoDto saveInfo(TourSpotDescriptionInfoDto tourSpotDescriptionInfoDto)
    throws SupportLanguageNotFoundException, TourSpotInfoNotFoundException,
           TourSpotDescriptionInfoAlreadyExistsException, TourSpotDescriptionNotFoundException,
           TourSpotDescriptionInfoNotEditableException, TourSpotDescriptionInfoNotFoundException;
    TourSpotDescriptionDto findByIdInDetail(Integer tourSpotDescriptionId)
    throws TourSpotDescriptionNotFoundException;
    Integer delete(Integer tourSpotDescriptionId) throws TourSpotDescriptionNotFoundException;
    void deleteInfo(Integer tourSpotDescriptionInfoId)
    throws TourSpotDescriptionInfoNotFoundException, TourSpotDescriptionInfoNotDeletableException;
}
