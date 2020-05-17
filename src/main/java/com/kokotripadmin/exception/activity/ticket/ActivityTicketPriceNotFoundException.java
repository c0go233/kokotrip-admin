package com.kokotripadmin.exception.activity.ticket;

public class ActivityTicketPriceNotFoundException extends Exception {

    public ActivityTicketPriceNotFoundException(Integer tourSpotTicketPriceId) {
        super("여행지 티켓가격(id=" + tourSpotTicketPriceId + ") 이 없는딩??");
    }
}
