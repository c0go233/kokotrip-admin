package com.kokotripadmin.service.entityinterfaces;

import com.kokotripadmin.entity.activity.Activity;
import com.kokotripadmin.entity.activity.ActivityInfo;
import com.kokotripadmin.exception.activity.ActivityInfoNotFoundException;
import com.kokotripadmin.exception.activity.ActivityNotFoundException;

import java.util.List;

public interface ActivityEntityService {

    Activity findEntityById(Integer activityId) throws ActivityNotFoundException;
    ActivityInfo findInfoEntityByIdAndSupportLanguageId(Integer activityId, Integer supportLanguageId)
    throws ActivityInfoNotFoundException;
    List<ActivityInfo> findAllInfoById(Integer activityId);
}
