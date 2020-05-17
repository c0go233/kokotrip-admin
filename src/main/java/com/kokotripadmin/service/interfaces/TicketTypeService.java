package com.kokotripadmin.service.interfaces;

import com.kokotripadmin.dto.ticket.TicketTypeDto;
import com.kokotripadmin.dto.ticket.TicketTypeInfoDto;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.ticket.*;

import java.util.LinkedHashMap;
import java.util.List;

public interface TicketTypeService {
    LinkedHashMap<Integer, String> findAllAsLinkedHashMap();
    Integer save(TicketTypeDto ticketTypeDto)
    throws SupportLanguageNotFoundException, TicketTypeNotFoundException, TicketTypeNameDuplicateException,
           TicketTypeInfoAlreadyExistsException;

    List<TicketTypeDto> findAll();
    TicketTypeDto findByIdInDetail(Integer ticketTypeId) throws TicketTypeNotFoundException;
    TicketTypeDto findById(Integer ticketTypeId) throws TicketTypeNotFoundException;

    TicketTypeInfoDto saveInfo(TicketTypeInfoDto ticketTypeInfoDto)
    throws SupportLanguageNotFoundException, TicketTypeNotFoundException, TicketTypeInfoAlreadyExistsException,
           TicketTypeInfoNotEditableException, TicketTypeInfoNotFoundException;

    void deleteInfo(Integer ticketTypeInfoId) throws TicketTypeInfoNotFoundException, TicketTypeInfoNotDeletableException;

    void delete(Integer ticketTypeId) throws TicketTypeNotFoundException;
}
