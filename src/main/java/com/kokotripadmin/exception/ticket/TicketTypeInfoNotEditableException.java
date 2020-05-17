package com.kokotripadmin.exception.ticket;

public class TicketTypeInfoNotEditableException extends Exception {

    public TicketTypeInfoNotEditableException() {
        super("한국어 번역정보는 편집가능하지 않습니다. 티켓타입정보를 직접편집하세요.");
    }
}
