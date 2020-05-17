package com.kokotripadmin.exception.ticket;

public class TicketTypeNotFoundException extends Exception {

    public TicketTypeNotFoundException() {
        super("티켓타입을 찾을수 없습니다.");
    }
}

