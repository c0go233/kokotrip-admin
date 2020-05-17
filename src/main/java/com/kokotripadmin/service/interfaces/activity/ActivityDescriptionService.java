package com.kokotripadmin.service.interfaces.activity;

import com.kokotripadmin.dto.activity.ActivityDescriptionDto;
import com.kokotripadmin.dto.activity.ActivityDescriptionInfoDto;
import com.kokotripadmin.exception.activity.*;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;

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
}
