package com.kokotripadmin.service.interfaces.activity;

import com.kokotripadmin.dto.activity.ActivityDescriptionDto;
import com.kokotripadmin.dto.activity.ActivityDescriptionImageDto;
import com.kokotripadmin.dto.activity.ActivityDescriptionInfoDto;
import com.kokotripadmin.exception.activity.*;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ActivityDescriptionService {

    ActivityDescriptionDto findById(Integer activityDescriptionId)
    throws ActivityDescriptionNotFoundException;
    Integer save(ActivityDescriptionDto activityDescriptionDto)
    throws ActivityNotFoundException, ActivityInfoNotFoundException, ActivityDescriptionNotFoundException,
           ActivityDescriptionAlreadyExistsException, SupportLanguageNotFoundException,
           ActivityDescriptionInfoAlreadyExistsException, ActivityDescriptionInfoNotFoundException;
    ActivityDescriptionInfoDto saveInfo(ActivityDescriptionInfoDto activityDescriptionInfoDto)
    throws SupportLanguageNotFoundException, ActivityInfoNotFoundException,
           ActivityDescriptionInfoAlreadyExistsException, ActivityDescriptionNotFoundException,
           ActivityDescriptionInfoNotEditableException, ActivityDescriptionInfoNotFoundException;

    ActivityDescriptionDto findByIdInDetail(Integer activityDescriptionId)
    throws ActivityDescriptionNotFoundException;
    void deleteInfo(Integer activityDescriptionInfoId)
    throws ActivityDescriptionInfoNotFoundException, ActivityDescriptionInfoNotDeletableException;
    Integer delete(Integer activityDescriptionId) throws ActivityDescriptionNotFoundException;



    void deleteImage(Integer imageId) throws ActivityDescriptionImageNotFoundException, RepImageNotDeletableException;
    Integer saveImage(ActivityDescriptionImageDto activityDescriptionImageDto)
    throws ActivityDescriptionNotFoundException, ImageDuplicateException, IOException, FileIsNotImageException;
    void updateImageOrder(List<Integer> imageIdList);
}
