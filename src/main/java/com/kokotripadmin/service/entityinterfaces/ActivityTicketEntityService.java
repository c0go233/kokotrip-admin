package com.kokotripadmin.service.entityinterfaces;

import com.kokotripadmin.entity.activity.ActivityTicket;
import com.kokotripadmin.entity.activity.ActivityTicketInfo;
import com.kokotripadmin.exception.activity.ticket.ActivityTicketInfoNotFoundException;
import com.kokotripadmin.exception.activity.ticket.ActivityTicketNotFoundException;

public interface ActivityTicketEntityService {

    ActivityTicket findEntityById(Integer tourSpotTicketId) throws ActivityTicketNotFoundException;
    ActivityTicketInfo findInfoEntityByIdAndSupportLanguageId(Integer tourSpotTicketId, Integer supportLanguageId)
    throws ActivityTicketInfoNotFoundException;
}
