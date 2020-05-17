package com.kokotripadmin.service.interfaces.activity;

import com.kokotripadmin.dto.activity.ActivityTicketDescriptionDto;
import com.kokotripadmin.dto.activity.ActivityTicketDescriptionInfoDto;
import com.kokotripadmin.exception.activity.ticket.*;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;

public interface ActivityTicketDescriptionService {


    ActivityTicketDescriptionDto findById(Integer activityTicketDescriptionId)
    throws ActivityTicketDescriptionNotFoundException;
    Integer save(ActivityTicketDescriptionDto activityTicketDescriptionDto)
    throws ActivityTicketDescriptionNotFoundException,
           ActivityTicketDescriptionAlreadyExistsException, SupportLanguageNotFoundException,
           ActivityTicketDescriptionInfoAlreadyExistsException, ActivityTicketDescriptionInfoNotFoundException,
           ActivityTicketNotFoundException, ActivityTicketInfoNotFoundException;

    ActivityTicketDescriptionDto findByIdInDetail(Integer activityTicketDescriptionId)
    throws ActivityTicketDescriptionNotFoundException;
    ActivityTicketDescriptionInfoDto saveInfo(ActivityTicketDescriptionInfoDto activityTicketDescriptionInfoDto)
    throws SupportLanguageNotFoundException, ActivityTicketDescriptionInfoAlreadyExistsException, ActivityTicketDescriptionNotFoundException,
           ActivityTicketDescriptionInfoNotEditableException, ActivityTicketDescriptionInfoNotFoundException,
           ActivityTicketInfoNotFoundException;

    Integer delete(Integer activityTicketDescriptionId) throws ActivityTicketDescriptionNotFoundException;
    void deleteInfo(Integer activityTicketDescriptionInfoId)
    throws ActivityTicketDescriptionInfoNotFoundException, ActivityTicketDescriptionInfoNotDeletableException;
}
