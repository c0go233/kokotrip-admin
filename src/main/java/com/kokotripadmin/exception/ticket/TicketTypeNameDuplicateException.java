package com.kokotripadmin.exception.ticket;

public class TicketTypeNameDuplicateException extends Exception {
    public TicketTypeNameDuplicateException(String newTicketTypeName) {
        super(newTicketTypeName + " 은(는) 이미 존재하는 티켓타입 입니다.");
    }
}
