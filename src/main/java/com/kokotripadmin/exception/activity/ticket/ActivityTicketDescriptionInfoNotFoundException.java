package com.kokotripadmin.exception.activity.ticket;

public class ActivityTicketDescriptionInfoNotFoundException extends Exception {

    public ActivityTicketDescriptionInfoNotFoundException() {
        super("여행지 티켓 설명 지원언어 정보를 찾을수 없습니다.");
    }
}
