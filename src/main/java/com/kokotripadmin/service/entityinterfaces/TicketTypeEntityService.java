package com.kokotripadmin.service.entityinterfaces;

import com.kokotripadmin.entity.ticket.TicketType;
import com.kokotripadmin.exception.ticket.TicketTypeNotFoundException;

public interface TicketTypeEntityService {

    TicketType findEntityById(Integer ticketTypeId) throws TicketTypeNotFoundException;
}
