package com.kokotripadmin.service.interfaces.activity;

import com.kokotripadmin.dto.activity.ActivityImageDto;
import com.kokotripadmin.dto.activity.ActivityTicketDto;
import com.kokotripadmin.dto.activity.ActivityTicketImageDto;
import com.kokotripadmin.dto.activity.ActivityTicketInfoDto;
import com.kokotripadmin.exception.activity.ActivityImageNotFoundException;
import com.kokotripadmin.exception.activity.ActivityInfoNotFoundException;
import com.kokotripadmin.exception.activity.ActivityNotFoundException;
import com.kokotripadmin.exception.activity.ticket.*;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.ticket.TicketTypeNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ActivityTicketService {

    Integer save(ActivityTicketDto activityTicketDto)
    throws ActivityTicketNotFoundException, SupportLanguageNotFoundException,
           ActivityNotFoundException, ActivityTicketNameDuplicateException, ActivityInfoNotFoundException,
           TicketTypeNotFoundException, ActivityTicketInfoAlreadyExistsException, ActivityTicketInfoNotFoundException;

    ActivityTicketInfoDto saveInfo(ActivityTicketInfoDto activityInfoDto)
    throws ActivityTicketNotFoundException, ActivityInfoNotFoundException, SupportLanguageNotFoundException,
           ActivityTicketInfoAlreadyExistsException, ActivityTicketInfoNotEditableException,
           ActivityTicketInfoNotFoundException;
    Integer delete(Integer activityTicketId) throws ActivityTicketNotFoundException;
    void deleteInfo(Integer activityTicketInfoId)
    throws ActivityTicketInfoNotFoundException, ActivityTicketInfoNotDeletableException;

    ActivityTicketDto findById(Integer activityTicketId) throws ActivityTicketNotFoundException;

    ActivityTicketDto findByIdInDetail(Integer activityTicketId) throws ActivityTicketNotFoundException;



    void deleteImage(Integer imageId) throws ActivityTicketImageNotFoundException, RepImageNotDeletableException;
    Integer saveImage(ActivityTicketImageDto activityTicketImageDto)
    throws ActivityTicketNotFoundException, ImageDuplicateException, IOException, FileIsNotImageException;
    void updateRepImage(Integer imageId) throws ActivityTicketImageNotFoundException;
    void updateImageOrder(List<Integer> imageIdList);
}
