package com.kokotripadmin.service.interfaces;


import com.kokotripadmin.dto.photozone.PhotoZoneDto;
import com.kokotripadmin.dto.photozone.PhotoZoneInfoDto;
import com.kokotripadmin.entity.photozone.PhotoZone;
import com.kokotripadmin.exception.activity.ActivityInfoNotFoundException;
import com.kokotripadmin.exception.activity.ActivityNotFoundException;
import com.kokotripadmin.exception.photozone.*;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tag.TagInfoNotFoundException;
import com.kokotripadmin.exception.tour_spot.TourSpotInfoNotFoundException;
import com.kokotripadmin.exception.tour_spot.TourSpotNotFoundException;

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

}
