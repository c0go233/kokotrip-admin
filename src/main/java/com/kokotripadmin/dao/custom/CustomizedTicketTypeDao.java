package com.kokotripadmin.dao.custom;

import com.kokotripadmin.entity.ticket.TicketType;

import java.util.List;

public interface CustomizedTicketTypeDao {

    List<TicketType> findAllByEnabled(boolean ticketTypeEnabled);
}
