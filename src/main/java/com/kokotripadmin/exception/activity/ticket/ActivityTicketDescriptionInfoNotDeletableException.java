package com.kokotripadmin.exception.activity.ticket;

public class ActivityTicketDescriptionInfoNotDeletableException extends Exception {

    public ActivityTicketDescriptionInfoNotDeletableException() {
        super("한국어 번역정보는 삭제할수없습니다. 여행지티켓설명을 삭제하세요.");
    }
}
