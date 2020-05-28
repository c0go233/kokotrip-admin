package com.kokotripadmin.service.interfaces;


import com.kokotripadmin.dto.photozone.PhotoZoneDto;
import com.kokotripadmin.dto.photozone.PhotoZoneImageDto;
import com.kokotripadmin.dto.photozone.PhotoZoneInfoDto;
import com.kokotripadmin.entity.photozone.PhotoZone;
import com.kokotripadmin.exception.activity.ActivityInfoNotFoundException;
import com.kokotripadmin.exception.activity.ActivityNotFoundException;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.photozone.*;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tag.TagInfoNotFoundException;
import com.kokotripadmin.exception.tour_spot.TourSpotInfoNotFoundException;
import com.kokotripadmin.exception.tour_spot.TourSpotNotFoundException;

import java.io.IOException;
import java.util.List;

public interface PhotoZoneService {

    PhotoZoneDto findById(Integer photoZoneId) throws PhotoZoneNotFoundException;
    PhotoZoneDto findByIdInDetail(Integer photoZoneId) throws PhotoZoneNotFoundException;
    Integer save(PhotoZoneDto photoZoneDto)
    throws TourSpotNotFoundException, PhotoZoneInfoAlreadyExistsException, TourSpotInfoNotFoundException,
           SupportLanguageNotFoundException, PhotoZoneNotFoundException, PhotoZoneDuplicateException,
           ActivityNotFoundException, ActivityInfoNotFoundException, PhotoZoneInfoNotFoundException,
           PhotoZoneTourSpotDuplicateException;
    PhotoZoneInfoDto saveInfo(PhotoZoneInfoDto photoZoneInfoDto)
    throws PhotoZoneNotFoundException, SupportLanguageNotFoundException, PhotoZoneInfoAlreadyExistsException,
           PhotoZoneInfoNotFoundException, TagInfoNotFoundException, PhotoZoneInfoNotEditableException,
           TourSpotInfoNotFoundException, ActivityInfoNotFoundException;
    void deleteInfo(Integer photoZoneInfoId)
    throws PhotoZoneInfoNotFoundException, PhotoZoneInfoNotDeletableException;
    Integer delete(Integer photoZoneId) throws PhotoZoneNotFoundException;





    void deleteImage(Integer imageId) throws PhotoZoneImageNotFoundException, RepImageNotDeletableException;
    Integer saveImage(PhotoZoneImageDto photoZoneImageDto)
    throws PhotoZoneNotFoundException, ImageDuplicateException, IOException, FileIsNotImageException;
    void updateRepImage(Integer imageId) throws PhotoZoneImageNotFoundException;
    void updateImageOrder(List<Integer> imageIdList);

}
