package com.kokotripadmin.service.interfaces.activity;

import com.kokotripadmin.dto.activity.ActivityDescriptionDto;
import com.kokotripadmin.dto.activity.ActivityDto;
import com.kokotripadmin.dto.activity.ActivityImageDto;
import com.kokotripadmin.dto.activity.ActivityInfoDto;
import com.kokotripadmin.dto.common.LocatableAutoCompleteDto;
import com.kokotripadmin.exception.activity.*;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tag.TagInfoNotFoundException;
import com.kokotripadmin.exception.tag.TagNotFoundException;
import com.kokotripadmin.exception.tour_spot.TourSpotInfoNotFoundException;
import com.kokotripadmin.exception.tour_spot.TourSpotNotFoundException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.io.IOException;
import java.util.List;

public interface ActivityService {

    Integer save(ActivityDto activityDto)
    throws TagNotFoundException, TourSpotInfoNotFoundException, TourSpotNotFoundException,
           ActivityNotFoundException, ActivityInfoNotFoundException, SupportLanguageNotFoundException,
           TagInfoNotFoundException, ActivityInfoAlreadyExistsException, ActivityNameDuplicateException;
    ActivityDto findById(Integer activityId) throws ActivityNotFoundException;
    ActivityInfoDto saveInfo(ActivityInfoDto activityInfoDto)
    throws ActivityInfoAlreadyExistsException, SupportLanguageNotFoundException, TourSpotInfoNotFoundException,
           TagInfoNotFoundException, ActivityNotFoundException, ActivityInfoNotFoundException,
           ActivityInfoNotEditableException;
    ActivityDto findByIdInDetail(Integer activityId) throws ActivityNotFoundException;
    DataTablesOutput<ActivityDto> findAllByPagination(DataTablesInput dataTablesInput);

    void deleteInfo(Integer activityInfoId) throws ActivityInfoNotFoundException, ActivityInfoNotDeletableException;

    Integer delete(Integer activityId) throws ActivityNotFoundException;

    List<LocatableAutoCompleteDto> findAllAsLocatableAutoComplete(String search);

    String getNameById(Integer activityId) throws ActivityNotFoundException;


    void deleteImage(Integer imageId) throws ActivityImageNotFoundException, RepImageNotDeletableException;
    Integer saveImage(ActivityImageDto activityImageDto)
    throws ActivityNotFoundException, ImageDuplicateException, IOException, FileIsNotImageException;
    void updateRepImage(Integer imageId) throws ActivityImageNotFoundException;
    void updateImageOrder(List<Integer> imageIdList);
}
