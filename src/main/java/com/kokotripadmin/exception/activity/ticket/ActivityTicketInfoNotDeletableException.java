package com.kokotripadmin.exception.activity.ticket;

public class ActivityTicketInfoNotDeletableException extends Exception {

    public ActivityTicketInfoNotDeletableException() {
        super("한국어 번역정보는 삭제할수없습니다. 여행지티켓을 삭제하세요.");
    }
}
