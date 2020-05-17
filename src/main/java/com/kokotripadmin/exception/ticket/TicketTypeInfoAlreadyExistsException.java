package com.kokotripadmin.exception.ticket;

public class TicketTypeInfoAlreadyExistsException extends Exception {

    public TicketTypeInfoAlreadyExistsException(String ticketTypeName, String supportLanguageName) {
        super("티켓타입: " + ticketTypeName + " 는(은) 이미 " + supportLanguageName + " 로 작성된 번역정보가 있습니다.");
    }
}
