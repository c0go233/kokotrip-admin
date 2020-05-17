package com.kokotripadmin.exception.ticket;

public class TicketTypeInfoNotDeletableException extends Exception {

    public TicketTypeInfoNotDeletableException() {
        super("한국어 번역정보는 삭제할수없습니다. 티켓타입을 삭제하세요.");
    }
}
