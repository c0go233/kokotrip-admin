package com.kokotripadmin.exception.ticket;

public class TicketTypeInfoNotFoundException extends Exception {

    public TicketTypeInfoNotFoundException() {
        super("타켓타입 번역정보를 찾지 못했습니다.");
    }
}
