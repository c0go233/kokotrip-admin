package com.kokotripadmin.service.interfaces.activity;

import com.kokotripadmin.dto.activity.ActivityTicketDto;
import com.kokotripadmin.dto.activity.ActivityTicketInfoDto;
import com.kokotripadmin.exception.activity.ActivityInfoNotFoundException;
import com.kokotripadmin.exception.activity.ActivityNotFoundException;
import com.kokotripadmin.exception.activity.ticket.*;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.ticket.TicketTypeNotFoundException;

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

}
