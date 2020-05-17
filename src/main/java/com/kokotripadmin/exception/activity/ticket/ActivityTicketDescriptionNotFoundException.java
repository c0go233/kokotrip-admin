package com.kokotripadmin.exception.activity.ticket;

public class ActivityTicketDescriptionNotFoundException extends Exception {

    public ActivityTicketDescriptionNotFoundException() {
        super("여행지 티켓 설명 정보를 찾을수 없습니다.");
    }
}
