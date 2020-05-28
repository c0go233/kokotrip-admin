package com.kokotripadmin.service.interfaces.tourspot;

import com.kokotripadmin.dto.common.GenericInfoDto;
import com.kokotripadmin.dto.tourspot.TourSpotDescriptionDto;
import com.kokotripadmin.dto.tourspot.TourSpotDescriptionImageDto;
import com.kokotripadmin.dto.tourspot.TourSpotDescriptionInfoDto;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tour_spot.*;

import java.io.IOException;
import java.util.List;

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



    void deleteImage(Integer imageId) throws TourSpotDescriptionImageNotFoundException;
    Integer saveImage(TourSpotDescriptionImageDto tourSpotDescriptionImageDto)
    throws TourSpotDescriptionNotFoundException, ImageDuplicateException, IOException, FileIsNotImageException;
    void updateImageOrder(List<Integer> imageIdList);
}
