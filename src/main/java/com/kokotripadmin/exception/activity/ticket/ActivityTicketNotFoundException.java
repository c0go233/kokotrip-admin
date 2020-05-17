package com.kokotripadmin.exception.activity.ticket;

public class ActivityTicketNotFoundException extends Exception {

    public ActivityTicketNotFoundException() {
        super("여행티켓이 존재하지않습니다.");
    }

    public ActivityTicketNotFoundException(Integer tourSpotTicketId) {
        super("여행지 티켓(id=" + tourSpotTicketId + ") 이 없는딩??");
    }
}
