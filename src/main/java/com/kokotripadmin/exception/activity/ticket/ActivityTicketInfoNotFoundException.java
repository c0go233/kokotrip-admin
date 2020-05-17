package com.kokotripadmin.exception.activity.ticket;

public class ActivityTicketInfoNotFoundException extends Exception {

    public ActivityTicketInfoNotFoundException() {
        super("여행지티켓 번역정보를 찾을수없습니다");
    }
}
